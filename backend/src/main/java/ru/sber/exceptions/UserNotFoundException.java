package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если токен не найден
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
