package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Модель для регистрации данных пользователя на сервисе KeyCloak
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RequestUser {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private List<Credential> credentials;
    private Attributes attributes;
}