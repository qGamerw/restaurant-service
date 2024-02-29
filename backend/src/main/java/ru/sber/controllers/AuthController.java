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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.model.*;
import ru.sber.proxies.KeyCloakProxy;
import ru.sber.proxies.KeyCloakProxyImp;
import ru.sber.services.JwtService;
import ru.sber.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для взаимодействия с регистрацией и входом у {@link User сотрудника}
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final KeyCloakProxy keyCloakProxy;

    @Autowired
    public AuthController(UserService userService,
                          JwtService jwtService,
                          KeyCloakProxy keyCloakProxy) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.keyCloakProxy = keyCloakProxy;
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Регистрация пользователя с email {}", signupRequest.getEmail());

        RequestUser requestUser = new RequestUser();
        requestUser.setUsername(signupRequest.getUsername());
        requestUser.setEmail(signupRequest.getEmail());
        requestUser.setEnabled(true);
        requestUser.setFirstName(signupRequest.getFirstName());
        requestUser.setLastName(signupRequest.getLastName());

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(signupRequest.getPhoneNumber());
        requestUser.setAttributes(attributes);

        Credential credential = new Credential();
        credential.setType(KeyCloakProxyImp.grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        requestUser.setCredentials(credentials);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<RequestUser> userEntity = new HttpEntity<>(requestUser, userHeaders);

        return keyCloakProxy.signUpUserREST(userEntity, signupRequest.getIdBranchOffice());
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        log.info("Вход пользователя с Username: {}", loginRequest.getUsername());

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", KeyCloakProxyImp.grantType);
        tokenBody.add("client_id", KeyCloakProxyImp.clientId);
        tokenBody.add("username", loginRequest.getUsername());
        tokenBody.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<>(tokenBody, tokenHeaders);
        return keyCloakProxy.signInUserREST(userEntity);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshUser(@RequestBody RefreshToken refreshToken) {
        log.info("Обновление токена у пользователя");

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", KeyCloakProxyImp.clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<>(tokenBody, tokenHeaders);
        return keyCloakProxy.signInUserREST(userEntity);
    }

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<UserDetails> getUserDetails() {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        Jwt jwt = getUserJwtTokenSecurityContext();

        var user = userService.findByContext();
        if (!user.getStatus().name().equals(EStatusEmployee.UNDER_CONSIDERATION.name())) {
            user.setStatus(EStatusEmployee.ACTIVE);
            userService.userUpdate(user);
        }

        var userDetails = new UserDetails(
                jwtService.getPreferredUsernameClaim(jwt),
                jwtService.getEmailClaim(jwt),
                jwtService.getPhoneNumberClaim(jwt),
                jwtService.getFirstNameClaim(jwt),
                jwtService.getLastNameClaim(jwt),
                user.getBranchOffice(),
                user.getStatus().name());

        return new ResponseEntity<>(userDetails, userHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('client_user')")
    @PutMapping
    @Transactional
    public ResponseEntity<?> updateUserInfo(@RequestBody SignupRequest signupRequest) throws JsonProcessingException {
        log.info("Обновляет данные о клиенте c email {}", signupRequest.getEmail());

        Jwt jwt = getUserJwtTokenSecurityContext();

        UpdateUserData updateUserData = new UpdateUserData();
        updateUserData.setEmail(Optional.ofNullable(signupRequest.getEmail())
                .orElseGet(() -> jwtService.getEmailClaim(jwt))
        );
        updateUserData.setFirstName(Optional.ofNullable(signupRequest.getFirstName())
                .orElseGet(() -> jwtService.getFirstNameClaim(jwt))
        );
        updateUserData.setLastName(Optional.ofNullable(signupRequest.getLastName())
                .orElseGet(() -> jwtService.getLastNameClaim(jwt))
        );

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(Optional.ofNullable(signupRequest.getPhoneNumber())
                .orElseGet(() -> jwtService.getPhoneNumberClaim(jwt))
        );
        updateUserData.setAttributes(attributes);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<UpdateUserData> userEntity = new HttpEntity<>(updateUserData, userHeaders);

        return keyCloakProxy.updateUserInfoREST(
                userEntity,
                jwtService.getSubClaim(getUserJwtTokenSecurityContext()),
                signupRequest.getIdBranchOffice());
    }

    @PutMapping("/logout")
    public ResponseEntity<String> logOutUser() {
        log.info("Выход пользователя");

        var user = userService.findByContext();
        if (!user.getStatus().name().equals(EStatusEmployee.UNDER_CONSIDERATION.name())) {
            user.setStatus(EStatusEmployee.INACTIVE);
            userService.userUpdate(user);
        }

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/reset-password/token")
    public ResponseEntity<?> sendPasswordToken(@RequestBody ResetPassword resetPassword) throws JsonProcessingException {
        log.info("Получение токена для изменения пароля у пользователя {}", resetPassword.getEmail());

        RequestResetPassword requestResetPassword = new RequestResetPassword();
        requestResetPassword.setType(KeyCloakProxyImp.grantType);
        requestResetPassword.setTemporary(false);
        String newPassword = resetPassword.getPassword();
        requestResetPassword.setValue(newPassword);

        HttpHeaders headersAdmin = getHttpHeadersAdmin();
        HttpEntity<RequestResetPassword> resetEntity = new HttpEntity<>(requestResetPassword, headersAdmin);

        return keyCloakProxy.sendPasswordTokenREST(resetEntity, resetPassword);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPassword resetPassword) throws JsonProcessingException {
        log.info("Обновление пароля у пользователя {}", resetPassword.getEmail());

        RequestResetPassword requestResetPassword = new RequestResetPassword();
        requestResetPassword.setType(KeyCloakProxyImp.grantType);
        requestResetPassword.setTemporary(false);
        String newPassword = resetPassword.getPassword();
        requestResetPassword.setValue(newPassword);

        HttpHeaders userHeaders = getHttpHeadersAdmin();
        HttpEntity<RequestResetPassword> resetEntity = new HttpEntity<>(requestResetPassword, userHeaders);

        return keyCloakProxy.resetPasswordREST(resetEntity, resetPassword);
    }

    /**
     * Получение токена из контекста
     */
    private Jwt getUserJwtTokenSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            return jwtAuthenticationToken.getToken();
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    private HttpHeaders getHttpHeadersAdmin() throws JsonProcessingException {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(signInUser(
                new LoginRequest(
                        KeyCloakProxyImp.adminRestaurantUsername,
                        KeyCloakProxyImp.adminRestaurantPassword)).getBody());

        String accessToken = rootNode.path("access_token").asText();
        userHeaders.setBearerAuth(accessToken);
        return userHeaders;
    }
}