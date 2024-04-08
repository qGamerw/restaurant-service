package ru.sber.proxies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sber.entities.OrderToken;
import ru.sber.exceptions.KeyCloakRESTException;
import ru.sber.exceptions.TokenResetInvalid;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.*;
import ru.sber.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Qualifier("keyCloak")
@Service
public class KeyCloak implements AuthProxy {
    public final static String orderServiceUsername = "user";
    public final static String orderServicePassword = "11111";
    public final static String adminRestaurantUsername = "admin";
    public final static String adminRestaurantPassword = "11111";

    public static final String clientId = "login-app";
    public static final String grantType = "password";
    private final UserService userService;
    @Value("${api-config.url.keycloakTokenUrl}")
    private String keycloakTokenUrl;
    @Value("${api-config.url.keycloakCreateUserUrl}")
    private String keycloakCreateUserUrl;
    @Value("${api-config.url.keycloakUpdateUserUrl}")
    private String keycloakUpdateUserUrl;
    @Value("${api-config.url.KeyCloakOrderToken}")
    private String KeyCloakOrderToken;

    @Autowired
    public KeyCloak(UserService userService) {
        this.userService = userService;
    }

    /**
     * Оформление регистрации пользователя
     *
     * @param signupRequest данные для регистрации пользователя
     * @return зарегистрированный пользователь
     */
    private static RequestUser getRegistrationUser(SignupRequest signupRequest) {
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
        credential.setType(KeyCloak.grantType);
        credential.setValue(signupRequest.getPassword());

        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        requestUser.setCredentials(credentials);
        return requestUser;
    }

    /**
     * Создание заголовка
     *
     * @param username логин
     * @param password пароль
     * @return заголовок
     */
    private static HttpEntity<MultiValueMap<String, String>> getHttpHeaderUser(String username, String password) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", grantType);
        tokenBody.add("client_id", clientId);
        tokenBody.add("username", username);
        tokenBody.add("password", password);

        return new HttpEntity<>(tokenBody, tokenHeaders);
    }

    /**
     * Настройка пароля пользователя
     *
     * @param resetPassword данные пароля
     * @return конфигурация
     */
    private static ResetPasswordRequest getResetPasswordRequest(ResetPassword resetPassword) {
        ResetPasswordRequest requestResetPassword = new ResetPasswordRequest();
        requestResetPassword.setType(KeyCloak.grantType);
        requestResetPassword.setTemporary(false);
        String newPassword = resetPassword.getPassword();
        requestResetPassword.setValue(newPassword);

        return requestResetPassword;
    }

    @Override
    public OrderToken updateOrderToken() {
        try {
            ResponseEntity<String> tokenResponseEntity = new RestTemplate().exchange(
                    KeyCloakOrderToken,
                    HttpMethod.POST,
                    getHttpHeaderUser(orderServiceUsername, orderServicePassword),
                    String.class
            );
            log.info("Статус запроса на получение токена: {}", tokenResponseEntity.getStatusCode());

            JsonNode jsonNode = new ObjectMapper().readTree(tokenResponseEntity.getBody());
            return new OrderToken(jsonNode.get("access_token").asText());
        } catch (Exception e) {
            log.error("Ошибка получения токена сервиса Заказы.");
            throw new KeyCloakRESTException("Ошибка получения токена сервиса Заказы");
        }
    }

    @Override
    public ResponseEntity<String> signUpUserREST(SignupRequest signupRequest) {
        try {
            HttpEntity<RequestUser> userEntity = new HttpEntity<>(
                    getRegistrationUser(signupRequest),
                    getHttpHeadersAdmin());

            return new RestTemplate().exchange(
                    keycloakCreateUserUrl, HttpMethod.POST, userEntity, String.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка регистрации: Не удалось получить токен администратора.");
            return new ResponseEntity<>("Ошибка регистрации: Не удалось получить токен администратора.", HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка регистрации: Логин или email заняты.");
            return new ResponseEntity<>("Ошибка регистрации: Логин или email заняты.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> signInUserREST(LoginRequest loginRequest) {
        try {
            return new RestTemplate().exchange(
                    keycloakTokenUrl,
                    HttpMethod.POST,
                    getHttpHeaderUser(loginRequest.getUsername(), loginRequest.getPassword()),
                    String.class);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка входа: Логин или пароль неверны.");
            return new ResponseEntity<>("Ошибка входа: Логин или пароль неверны.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> refreshTokenREST(RefreshToken refreshToken) {
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "refresh_token");
        tokenBody.add("client_id", KeyCloak.clientId);
        tokenBody.add("refresh_token", refreshToken.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        try {
            return new RestTemplate().exchange(
                    keycloakTokenUrl,
                    HttpMethod.POST,
                    userEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка обновления токена: Неверный токен пользователя.");
            return new ResponseEntity<>("Ошибка обновления токена: Неверный токен пользователя.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateUserInfoREST(
            SignupRequest signupRequest,
            UpdateUserData updateUserData,
            String idUser) {
        try {
            HttpEntity<UpdateUserData> userEntity = new HttpEntity<>(
                    updateUserData,
                    getHttpHeadersAdmin());

            return new RestTemplate().exchange(
                    keycloakUpdateUserUrl + idUser,
                    HttpMethod.PUT,
                    userEntity,
                    String.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка обновления информации пользователя: Не удалось получить токен администратора.");
            return new ResponseEntity<>(
                    "Ошибка обновления информации пользователя: Не удалось получить токен администратора.",
                    HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка обновления информации пользователя: Логин или email заняты.");
            return new ResponseEntity<>("Ошибка обновления информации пользователя: Логин или email заняты.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> sendPasswordToken(ResetPassword resetPassword) {
        ResetPasswordRequest requestResetPassword = getResetPasswordRequest(resetPassword);

        try {
            HttpEntity<ResetPasswordRequest> resetEntity = new HttpEntity<>(
                    requestResetPassword,
                    getHttpHeadersAdmin());

            return new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET,
                    resetEntity,
                    String.class);
        } catch (UserNotFoundException e) {
            log.error("Ошибка отправки токена: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка отправки токена: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            log.error("Ошибка отправки пароля: Не удалось получить токен администратора.");
            return new ResponseEntity<>("Ошибка сброса пароля: Не удалось получить токен администратора.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateUserPassword(ResetPassword resetPassword) {
        ResetPasswordRequest resetPasswordRequest = getResetPasswordRequest(resetPassword);

        try {
            HttpEntity<ResetPasswordRequest> resetEntity = new HttpEntity<>(
                    resetPasswordRequest,
                    getHttpHeadersAdmin());

            ResponseEntity<String> userResponseEntity = new RestTemplate().exchange(
                    keycloakCreateUserUrl + "?email=" + resetPassword.getEmail(),
                    HttpMethod.GET,
                    resetEntity,
                    String.class);
            log.info("Статус запроса получение токена пользователя при обновлении пароля: {}", userResponseEntity.getStatusCode());

            JsonNode usersNode = new ObjectMapper().readTree(userResponseEntity.getBody());
            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFoundException("Ошибка сброса пароля: Пользователь не найден");
            }

            JsonNode userNode = usersNode.get(0);
            String userId = userNode.get("id").asText();

            if (resetPassword.getToken().trim().equals(userService.getUserToken(userId))) {
                userService.deleteTokenById(userId);
            } else {
                throw new TokenResetInvalid("Ошибка сброса пароля: Неверный токен сброса.");
            }

            return new RestTemplate().exchange(
                    keycloakUpdateUserUrl + userId + "/reset-password",
                    HttpMethod.PUT,
                    resetEntity,
                    String.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сброса пароля: Не удалось получить токен администратора.");
            return new ResponseEntity<>("Ошибка сброса пароля: Не удалось получить токен администратора.", HttpStatus.BAD_REQUEST);
        } catch (TokenResetInvalid e) {
            log.error("Ошибка сброса пароля: Неверный токен сброса.");
            return new ResponseEntity<>("Ошибка сброса пароля: Неверный токен сброса.", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            log.error("Ошибка сброса пароля: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка сброса пароля: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String idUser, Jwt jwt) {
        try {
            HttpHeaders headers = getHttpHeadersAdmin();

            return new RestTemplate().exchange(
                    keycloakUpdateUserUrl + idUser,
                    HttpMethod.DELETE,
                    new HttpEntity<>(headers),
                    String.class);
        } catch (JsonProcessingException e) {
            log.error("Ошибка удаления: Не удалось получить токен администратора.");
            return new ResponseEntity<>("Ошибка удаления: Не удалось получить токен администратора.", HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка удаления: {}", e.getMessage());
            return new ResponseEntity<>("Ошибка удаления: " + e.getMessage(), e.getStatusCode());
        }

    }

    /**
     * Заголовок администратора
     *
     * @return заголовок
     * @throws JsonProcessingException JsonProcessingException
     */
    private HttpHeaders getHttpHeadersAdmin() throws JsonProcessingException, HttpClientErrorException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = new RestTemplate().exchange(
                keycloakTokenUrl,
                HttpMethod.POST,
                getHttpHeaderUser(KeyCloak.adminRestaurantUsername, KeyCloak.adminRestaurantPassword),
                String.class);

        JsonNode rootNode = objectMapper.readTree(response.getBody());
        String accessToken = rootNode.path("access_token").asText();

        return new HttpHeaders() {{
            setContentType(MediaType.APPLICATION_JSON);
            setBearerAuth(accessToken);
        }};
    }
}
