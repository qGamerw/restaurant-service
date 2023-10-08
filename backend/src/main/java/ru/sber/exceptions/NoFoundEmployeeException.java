package ru.sber.exceptions;

/**
 * Исключения для несуществующего пользователя
 */
public class NoFoundEmployeeException extends RuntimeException {
    public NoFoundEmployeeException(String message) {
        super(message);
    }
}