package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель для хранения информации о логине и пароле при входе
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
