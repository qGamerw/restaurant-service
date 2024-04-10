package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Атрибуты пользователя для сервиса KeyCloak
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Attributes {
    private String phoneNumber;
}