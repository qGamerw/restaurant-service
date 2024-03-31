package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если не прошел запрос на сервис KEyCloak
 */
public class KeyCloakRESTException extends RuntimeException {
      public KeyCloakRESTException(String message) {
        super(message);
    }
}
