package ru.sber.observer;

import jakarta.mail.MessagingException;
import ru.sber.models.ResetPassword;

/**
 * Сервис отправки письма на электронную почту
 */
public interface EmailObserver {
    /**
     * Отправляет на почту сообщение с токен
     *
     * @param resetPassword данные для отправки сообщения
     */
    void sendResetPasswordToken(ResetPassword resetPassword) throws MessagingException;

    /**
     * Отправляет на почту сообщение с токен
     *
     * @param resetPassword электронная почта
     */
    void sendUpdatePasswordToken(ResetPassword resetPassword) throws MessagingException;
}
