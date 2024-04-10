package ru.sber.security;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.sber.entities.BranchOffice;
import ru.sber.models.UpdateUserData;
import ru.sber.models.UserDetails;

import java.util.List;

/**
 * Класс для получения информации из токена
 */
public interface JwtService {

    /**
     * Получение id пользователя
     *
     * @param jwt токен
     * @return Id пользователя
     */
    String getSubClaim(Jwt jwt);

    /**
     * Получение роли пользователя
     *
     * @param jwt токен
     * @return Список ролей пользователя
     */
    List<String> getRoleClaim(Jwt jwt);

    /**
     * Получение email пользователя
     *
     * @param jwt токен
     * @return Email пользователя
     */
    String getEmailClaim(Jwt jwt);

    /**
     * Получение номера телефона пользователя
     *
     * @param jwt токен
     * @return Номер телефона пользователя
     */
    String getPhoneNumberClaim(Jwt jwt);

    /**
     * Получение логина пользователя
     *
     * @param jwt токен
     * @return Логин пользователя
     */
    String getPreferredUsernameClaim(Jwt jwt);

    /**
     * Получение имя пользователя
     *
     * @param jwt токен
     * @return Имя пользователя
     */
    String getFirstNameClaim(Jwt jwt);

    /**
     * Получение фамилия пользователя
     *
     * @param jwt токен
     * @return Фамилия пользователя
     */
    String getLastNameClaim(Jwt jwt);

    /**
     * Получение модели данных пользователя
     *
     * @param jwt          токен
     * @param branchOffice офис
     * @param status       статус
     * @return Модель данных пользователя
     */
    UserDetails getDataUser(Jwt jwt, BranchOffice branchOffice, String status);

    /**
     * Получение модели данных на обновления пользователя
     *
     * @param jwt токен
     * @return Модели данных на обновления пользователя
     */
    UpdateUserData getDataUserByContext(Jwt jwt);
}
