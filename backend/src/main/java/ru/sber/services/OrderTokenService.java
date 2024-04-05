package ru.sber.services;

import ru.sber.entities.OrderToken;


/**
 * Сервис для взаимодействия с {@link OrderToken токенами}
 */
public interface OrderTokenService {
    /**
     * Получение токена
     *
     * @return Токен
     */
    OrderToken getToken();
}
