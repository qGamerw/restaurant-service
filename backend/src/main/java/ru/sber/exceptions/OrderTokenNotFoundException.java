package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если не удалось найти токен в базе данных для сервиса Заказы
 */
public class OrderTokenNotFoundException extends RuntimeException {
    public OrderTokenNotFoundException(String message) {
        super(message);
    }
}
