package ru.sber.services;

import ru.sber.entities.Notify;
import ru.sber.exceptions.UserNotApproved;

/**
 * Сервис для взаимодействия с {@link Notify уведомлениями}
 */
public interface NotifyService {
    /**
     * Добавляет информацию для уведомления
     *
     * @param id id
     */
    void addNotify(Long id);

    /**
     * Получает информацию о заказах после уведомления
     *
     * @return String
     * @throws UserNotApproved пользователь не подтвержден
     */
    String getListId() throws UserNotApproved;
}

