package ru.sber.exceptions;

/**
 * Стоит выбрасывать если не удалось сгенерировать токен
 */
public class BranchOfficeNotFoundException extends RuntimeException {
      public BranchOfficeNotFoundException(String message) {
        super(message);
    }
}
