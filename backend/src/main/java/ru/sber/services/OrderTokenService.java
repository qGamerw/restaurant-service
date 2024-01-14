package ru.sber.services;

import ru.sber.entities.OrderToken;

/**
 * Сервис для взаимодействия с {@link OrderToken токенами}
 */
public interface OrderTokenService {
    /**
     * Сохранение токена
     *
     * @param orderToken токен
     * @return Результат
     */
    boolean save(OrderToken orderToken);

    /**
     * Получение токена
     *
     * @return Токен
     */
    OrderToken getToken();
}
