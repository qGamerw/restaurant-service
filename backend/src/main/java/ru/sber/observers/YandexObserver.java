package ru.sber.observers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.sber.models.ResetPassword;

/**
 * Реализация отправки на yandex сервис
 */
@Slf4j
@Service
@Data
public class YandexObserver implements EmailObserver {
    private final JavaMailSender javaMailSender;
    @Value("${jwt.email-address.address}")
    private String emailAddress;

    @Autowired
    public YandexObserver(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendResetPasswordToken(ResetPassword resetPassword) throws MessagingException {
        log.info("Отправляем токен: {}", resetPassword.getEmail());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailAddress);

        helper.setTo(resetPassword.getEmail());
        helper.setSubject("Привет!");
        helper.setText(String.format("""
                Мы получили запрос на сброса пароля для твоего аккаунта. Чтобы восстановить пароль, введи следующий токен:
                                                           \s
                Токен для сброса пароля: %s
                                                           \s
                Если ты не запрашивал восстановление пароля, проигнорируй это сообщение. Если сам запросил восстановление, пожалуйста, следуй инструкциям далее.
                                                           \s
                С уважением,
                Команда поддержки сайта
               \s""", resetPassword.getToken()));
        javaMailSender.send(message);
    }

    @Override
    public void sendUpdatePasswordToken(ResetPassword resetPassword) throws MessagingException {
        log.info("Отправляем пароль: {}", resetPassword.getEmail());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailAddress);

        helper.setTo(resetPassword.getEmail());
        helper.setSubject("Привет!");
        helper.setText(String.format("""
                Мы получили запрос на обновление пароля для твоего аккаунта.
                                                           \s
                Новый пароль: %s
                                                           \s
                С уважением,
                Команда поддержки сайта
               \s""", resetPassword.getPassword()));
        javaMailSender.send(message);
    }
}
