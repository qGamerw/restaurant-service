package ru.sber.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.sber.entities.User;
import ru.sber.entities.request.LoginRequest;
import ru.sber.entities.request.SignupRequest;
import ru.sber.exceptions.UserNotFound;
import ru.sber.model.*;
import ru.sber.repositories.UserRepository;
import ru.sber.services.JwtService;
import ru.sber.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для взаимодействия с регистрацией и входом у {@link User сотрудника}
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String keycloakTokenUrl = "http://localhost:8080/realms/restaurant-realm/protocol/openid-connect/token";
    private final String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/restaurant-realm/users";
    private final String clientId = "login-app";
    private final String grantType = "password";
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/signup")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) {
        Jwt jwtToken = getUserJwtTokenSecurityContext();

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signupRequest.getUsername());
        userRequest.setEmail(signupRequest.getEmail());
        userRequest.setEnabled(true);

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(signupRequest.getPhoneNumber());
        userRequest.setAttributes(attributes);

        Credential credential = new Credential();
        credential.setType(grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        userRequest.setCredentials(credentials);

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        userHeaders.setBearerAuth(jwtToken.getTokenValue());

        HttpEntity<UserRequest> userEntity = new HttpEntity<>(userRequest, userHeaders);

        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);

            String responseHeader = userResponseEntity.getHeaders().get("Location").get(0);

            int lastSlashIndex = responseHeader.lastIndexOf("/");
            String userId = responseHeader.substring(lastSlashIndex + 1);

            userService.addUserById(userId, signupRequest.getIdBranchOffice());

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        log.info("Вход пользователя с Username: {}", loginRequest.getUsername());

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        return getStringResponseEntity(tokenHeaders, tokenBody);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshUser(@RequestBody RefreshToken refreshToken) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        return getStringResponseEntity(tokenHeaders, tokenBody);
    }

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<String> getUserDetails() {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);

        Jwt jwt = getUserJwtTokenSecurityContext();
        var user = userRepository.findById(jwtService.getSubClaim(jwt)).orElseThrow(() -> new UserNotFound("Пользователь не найден"));
        UserDetails userDetails = new UserDetails(
                jwtService.getPreferredUsernameClaim(jwt),
                jwtService.getEmailClaim(jwt),
                jwtService.getPhoneNumberClaim(jwt),
                user.getBranchOffice(),
                user.getStatus().name());

        ObjectMapper objectMapper = new ObjectMapper();
        String userDetailsJson;
        try {
            userDetailsJson = objectMapper.writeValueAsString(userDetails);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Информация о пользователе при обработке ошибок ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(userDetailsJson, userHeaders, HttpStatus.OK);
    }

    private ResponseEntity<String> getStringResponseEntity(HttpHeaders tokenHeaders, MultiValueMap<String, String> tokenBody) {
        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, tokenEntity, String.class);

//            String jsonResponse = tokenResponseEntity.getBody();
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//
//            String accessToken = jsonNode.get("access_token").asText();

            return new ResponseEntity<>(tokenResponseEntity.getBody(),
                    tokenResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получение токена из контекста
     */
    private Jwt getUserJwtTokenSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            return jwtAuthenticationToken.getToken();
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }
}