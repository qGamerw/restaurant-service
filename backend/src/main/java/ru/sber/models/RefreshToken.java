package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель для хранения информации при обновлении токена пользователя
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RefreshToken {
    private String refresh_token;
}