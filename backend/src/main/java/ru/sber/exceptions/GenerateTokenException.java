package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если не удалось сгенерировать токен
 */
public class GenerateTokenException extends RuntimeException {
    public GenerateTokenException(String message) {
        super(message);
    }
}
