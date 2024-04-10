package ru.sber.services;

import ru.sber.entities.BranchOffice;
import ru.sber.entities.User;
import ru.sber.exceptions.BranchOfficeNotFoundException;
import ru.sber.exceptions.UserNotFoundException;

/**
 * Сервис для взаимодействия с {@link User сотрудником}
 */
public interface UserService {

    /**
     * Сохраняет данные сотрудника
     *
     * @param userId         id сотрудник
     * @param idBranchOffice id филиала
     * @throws BranchOfficeNotFoundException филиал не найден
     */
    void addUserById(String userId, String idBranchOffice) throws BranchOfficeNotFoundException;

    /**
     * Удаляет сотрудника по id
     *
     * @param userId id пользователя
     * @return результат
     */
    boolean deleteById(String userId);

    /**
     * Ищет пользователя по id
     *
     * @param userId id пользователя
     * @return пользователь
     * @throws UserNotFoundException пользователь не найден
     */
    User findById(String userId) throws UserNotFoundException;

    /**
     * Ищет пользователя в контексте
     *
     * @return пользователь
     * @throws UserNotFoundException пользователь не найден
     */
    User findByContext() throws UserNotFoundException;

    /**
     * Изменяет статус при выходе у пользователя
     */
    void logOutUser();

    /**
     * Обновляет данные сотрудника
     *
     * @param user сотрудник
     */
    void userUpdate(User user);

    /**
     * Возвращает токен для сброса пароля по id пользователя
     *
     * @param userId id пользователя
     * @return Токен для сброса пароля
     */
    String getUserToken(String userId);

    /**
     * Удаляет токен сброса пароля у сотрудника по id
     *
     * @param userId id пользователя
     */
    void deleteTokenById(String userId);

    /**
     * Возвращает пользователя
     *
     * @return пользователь
     * @throws UserNotFoundException пользователь не найден
     */
    User getUser() throws UserNotFoundException;

    /**
     * Возвращает филиал
     *
     * @return филиал
     */
    BranchOffice getBranchOffice();

    /**
     * Возвращает количество активных сотрудников
     *
     * @return количество активных сотрудников
     */
    int countActiveUserByBranchOffice();
}
