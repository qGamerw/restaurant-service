package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если пользователь не одобрен
 */
public class TokenResetInvalid extends RuntimeException {
    public TokenResetInvalid(String message) {
        super(message);
    }
}
