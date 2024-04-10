package ru.sber.proxies;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.sber.entities.OrderToken;
import ru.sber.models.*;

/**
 * Прокси для взаимодействия с сервисом KeyCloak
 */
public interface AuthProxy {
    /**
     * Обновляет токен сервиса Заказы
     *
     * @return Токен
     */
    OrderToken updateOrderToken();

    /**
     * Регистрирует пользователя
     *
     * @param signupRequest данные для регистрации
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> signUpUserREST(SignupRequest signupRequest);

    /**
     * Аутентифицирует пользователя
     *
     * @param loginRequest данные для входа
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> signInUserREST(LoginRequest loginRequest);

    /**
     * Обновляет токен пользователя
     *
     * @param refreshToken токен обновления
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> refreshTokenREST(RefreshToken refreshToken);

    /**
     * Обновляет данные у пользователя
     *
     * @param signupRequest новые данные
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> updateUserInfoREST(SignupRequest signupRequest, UpdateUserData updateUserData, String idUser);

    /**
     * Отправляет токен на смену пароля у пользователя
     *
     * @param resetPassword данные о сбросе пароля
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> sendPasswordToken(ResetPassword resetPassword);

    /**
     * Обновляет пароль у пользователя
     *
     * @param resetPassword данные о смене пароля
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> updateUserPassword(ResetPassword resetPassword);

    /**
     * Удаление пользователя
     *
     * @return Ответ сервиса KeyCloak
     */
    ResponseEntity<String> deleteUser(String idUser, Jwt jwt);
}