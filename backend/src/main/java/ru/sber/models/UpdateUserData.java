package ru.sber.models;

import lombok.Data;

/**
 * Модель для обновления данных о пользователе
 */
@Data
public class UpdateUserData {
    private String firstName;
    private String lastName;
    private String email;
    private Attributes attributes;
}
