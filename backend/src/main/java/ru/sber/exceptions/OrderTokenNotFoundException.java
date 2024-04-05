package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если не удалось найти токен в базе данных для сервиса Заказы
 */
public class OrderTokenNotFoundException extends RuntimeException {
    //TODO: Не забыть привязать
    public OrderTokenNotFoundException(String message) {
        super(message);
    }
}
