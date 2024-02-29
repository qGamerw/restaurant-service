package ru.sber.proxies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.sber.config.GenerateTokenPasswordSequence;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.OrderToken;
import ru.sber.exceptions.KeyCloakRESTException;
import ru.sber.exceptions.UserInvalidToken;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.model.RequestResetPassword;
import ru.sber.model.RequestUser;
import ru.sber.model.ResetPassword;
import ru.sber.model.UpdateUserData;
import ru.sber.repositories.OrderTokenRepository;
import ru.sber.services.EmailService;
import ru.sber.services.OrderTokenService;
import ru.sber.services.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class KeyCloakProxyImp implements KeyCloakProxy {
    public final static String orderServiceUsername = "user";
    public final static String orderServicePassword = "11111";
    public final static String adminRestaurantUsername = "admin";
    public final static String adminRestaurantPassword = "11111";
    public final static String clientId = "login-app";
    public final static String grantType = "password";

    private final static String keycloakTokenUrl = "http://localhost:8080/realms/restaurant-realm/protocol/openid-connect/token";
    private final static String keycloakCreateUserUrl = "http://localhost:8080/admin/realms/restaurant-realm/users";
    private final static String keycloakUpdateUserUrl = "http://localhost:8080/admin/realms/restaurant-realm/users/";

    private final OrderTokenRepository orderTokenRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public KeyCloakProxyImp(OrderTokenRepository orderTokenRepository,
                            UserService userService,
                            EmailService emailService) {
        this.orderTokenRepository = orderTokenRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<String> signUpUserREST(HttpEntity<RequestUser> userEntity, String idBranchOffice) {
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);

            String responseHeader = userResponseEntity.getHeaders().get("Location").get(0);

            int lastSlashIndex = responseHeader.lastIndexOf("/");
            String userId = responseHeader.substring(lastSlashIndex + 1);

            userService.addUserById(userId, idBranchOffice);

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> signInUserREST(HttpEntity<MultiValueMap<String, String>> userEntity) {
        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    keycloakTokenUrl, HttpMethod.POST, userEntity, String.class);

            return new ResponseEntity<>(tokenResponseEntity.getBody(), tokenResponseEntity.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateUserInfoREST(HttpEntity<UpdateUserData> userEntity, String idUser, String idBranchOffice) {
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl + idUser,
                    HttpMethod.PUT, userEntity, String.class);
            log.info("Результат отправки на keycloak: {}", userResponseEntity.getStatusCode());

            var user = userService.findByContext();
            user.setBranchOffice(idBranchOffice != null ?
                    new BranchOffice(Long.parseLong(idBranchOffice)) : user.getBranchOffice());

            userService.userUpdate(user);

            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> sendPasswordTokenREST(HttpEntity<RequestResetPassword> resetEntity, ResetPassword resetPassword) {
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET, resetEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userResponseEntity.getBody());

            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFoundException("Пользователь не найден");
            }

            JsonNode userNode = usersNode.get(0);
            var user = userService.findById(userNode.get("id").asText());
            String passwordToken = GenerateTokenPasswordSequence.generateToken();
            user.setResetPasswordToken(passwordToken);
            userService.userUpdate(user);
            resetPassword.setToken(passwordToken);

            emailService.sendResetPasswordToken(resetPassword);
            return new ResponseEntity<>(userResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Void> resetPasswordREST(HttpEntity<RequestResetPassword> resetEntity, ResetPassword resetPassword) {
        try {
            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET, resetEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode usersNode = objectMapper.readTree(userResponseEntity.getBody());
            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFoundException("Пользователь не найден");
            }
            JsonNode userNode = usersNode.get(0);
            String userId = userNode.get("id").asText();

            if (!resetPassword.getToken().trim().equals(userService.getUserToken(userId))) {
                throw new UserInvalidToken("Неверный токен");
            } else {
                userService.deleteTokenById(userId);
            }

            ResponseEntity<String> resetResponseEntity = new RestTemplate().exchange(
                    keycloakUpdateUserUrl + userId + "/reset-password", HttpMethod.PUT, resetEntity, String.class);
            log.info("Результат отправки на keycloak: {}", resetResponseEntity.getStatusCode());

            emailService.sendUpdatePasswordToken(resetPassword);
            return new ResponseEntity<>(resetResponseEntity.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public OrderToken updateOrderToken() {
        log.info("Обновление токена заказа");

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", orderServiceUsername);
        tokenBody.add("password", orderServicePassword);

        HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    "http://localhost:8080/realms/order-realm/protocol/openid-connect/token",
                    HttpMethod.POST, tokenEntity, String.class);

            String jsonResponse = tokenResponseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);


            String accessToken = jsonNode.get("access_token").asText();
            log.info("Добавление в базу данных токена {}", accessToken);

            OrderToken orderToken = new OrderToken(accessToken);
            orderTokenRepository.save(orderToken);
            return orderToken;

        } catch (Exception e) {
            e.printStackTrace();
            throw new KeyCloakRESTException("Ошибка при отправке запроса на получения токена");
        }
    }
}
