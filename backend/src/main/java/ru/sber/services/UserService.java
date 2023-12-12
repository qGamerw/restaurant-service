package ru.sber.services;

import ru.sber.entities.User;

/**
 * Сервис для взаимодействия с {@link User сотрудником}
 */
public interface UserService {

    /**
     * Сохраняет данные сотрудника
     *
     * @param userId id сотрудник
     */
    boolean addUserById(String userId, String idBranchOffice);

    /**
     * Удаляет сотрудника по id
     *
     * @param id id сотрудника
     * @return boolean
     */
    boolean deleteById(String id);
}
