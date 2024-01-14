package ru.sber.exceptions;

/**
 * Стоит выбрасывать если не удалось найти токен в базе данных
 */
public class OrderTokenNotFoundException extends RuntimeException {
      public OrderTokenNotFoundException(String message) {
        super(message);
    }
}
