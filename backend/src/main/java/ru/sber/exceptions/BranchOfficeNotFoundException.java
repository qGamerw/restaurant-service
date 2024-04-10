package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если филиал не найден
 */
public class BranchOfficeNotFoundException extends RuntimeException {
    public BranchOfficeNotFoundException(String message) {
        super(message);
    }
}
