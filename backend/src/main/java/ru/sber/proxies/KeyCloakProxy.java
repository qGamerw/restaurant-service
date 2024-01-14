package ru.sber.proxies;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import ru.sber.model.RequestResetPassword;
import ru.sber.model.RequestUser;
import ru.sber.model.ResetPassword;
import ru.sber.model.UpdateUserData;

/**
 * Прокси для взаимодействия с сервисом KeyCloak
 */
public interface KeyCloakProxy {
    /**
     * Проверяет актуальность токена
     *
     * @return токен
     */
    String checkingValidityOfToken();

    /**
     * Отравляет запрос на регистрацию
     *
     * @param userEntity     данные для запроса
     * @param idBranchOffice id филиала
     * @return ответ сервиса KeyCloak
     */
    ResponseEntity<String> signUpUserREST(HttpEntity<RequestUser> userEntity, String idBranchOffice);

    /**
     * Входит на сервис KetCloak
     *
     * @param userEntity данные для запроса
     * @return ответ сервиса KeyCloak
     */
    ResponseEntity<String> signInUserREST(HttpEntity<MultiValueMap<String, String>> userEntity);

    /**
     * Обновляет данные у пользователя
     *
     * @param userEntity     данные для запроса
     * @param idUser         id пользователя
     * @param idBranchOffice id филиала
     * @return ответ сервиса KeyCloak
     */
    ResponseEntity<?> updateUserInfoREST(HttpEntity<UpdateUserData> userEntity, String idUser, String idBranchOffice);

    /**
     * Отправляет токен на смену пароля у пользователя
     *
     * @param resetEntity   данные для запроса
     * @param resetPassword модель хранения информации о смене пароля
     * @return ответ сервиса KeyCloak
     */
    ResponseEntity<?> sendPasswordTokenREST(HttpEntity<RequestResetPassword> resetEntity, ResetPassword resetPassword);

    /**
     * Обновляет пароль у пользователя
     *
     * @param resetEntity   данные для запроса
     * @param resetPassword модель хранения информации о смене пароля
     * @return ответ сервиса KeyCloak
     */
    ResponseEntity<Void> resetPasswordREST(HttpEntity<RequestResetPassword> resetEntity, ResetPassword resetPassword);
}