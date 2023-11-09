package ru.sber.services;

import ru.sber.entities.Position;

/**
 * Сервис для взаимодействия с {@link Position должностью}
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

