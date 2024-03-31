package ru.sber.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Настройка конфигурации почты
 */
@Configuration
public class EmailConfig {
    @Value("${jwt.email-address.address}")
    private String emailAddress;
    @Value("${jwt.email-address.password}")
    private String resourceId;

    /**
     * Настройка Yandex почты:
     * https://yandex.ru/support/mail/mail-clients/others.html#smtpsetting
     *
     * @return JavaMailSender конфигурация
     */
    @Bean
    public JavaMailSender yandexMailSenderSMTP() {

        String host = "smtp.yandex.ru";

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(465);
        mailSender.setUsername(emailAddress);
        mailSender.setPassword(resourceId);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}