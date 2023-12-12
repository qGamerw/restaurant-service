package ru.sber.services;

import ru.sber.entities.Role;

/**
 * Сервис для взаимодействия с {@link Role должностью}
 */
public interface NotifyService {
    /**
     * Добавляет информацию для уведомления
     *
     * @param id id
     * @return long
     */
    long addNotify(Long id);

    /**
     * Получает информацию о заказах после уведомления
     *
     * @return String
     */
    String getList();
}

