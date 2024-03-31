package ru.sber.exceptions;

/**
 * Стоит выбрасывать, если у пользователя недостаточно прав на выполнение команды
 */
public class UserNotApproved extends RuntimeException {
      public UserNotApproved(String message) {
        super(message);
    }
}
