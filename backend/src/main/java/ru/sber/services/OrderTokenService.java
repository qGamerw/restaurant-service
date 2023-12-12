package ru.sber.services;

import ru.sber.entities.OrderToken;

import java.util.List;

/**
 * Сервис для взаимодействия с {@link OrderToken токенами}
 */
public interface OrderTokenService {
    /**
     * Сохранить токен
     *
     * @param orderToken токен
     * @return boolean
     */
    boolean save(OrderToken orderToken);

    /**
     * Получить все токены
     *
     * @return List<OrderToken>
     */
    List<OrderToken> findAll();
}
