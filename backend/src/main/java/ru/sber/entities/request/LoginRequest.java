package ru.sber.entities.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Получение REST данных для входа {@link ru.sber.entities.Employee сотрудника}
 */
@Data
public class LoginRequest {
    @NotBlank
    private String employeeName;

    @NotBlank
    private String password;
}
