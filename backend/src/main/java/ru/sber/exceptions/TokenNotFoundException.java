package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если токен не найден
 */
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }
}
