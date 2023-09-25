package ru.sber.exceptions;

/**
 * Исключения для несуществующей категории
 */
public class NoFoundCategoryException extends RuntimeException {
    public NoFoundCategoryException(String message) {
        super(message);
    }
}