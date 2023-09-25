package ru.sber.entities.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.sber.entities.Position;

/**
 * Получение REST данных для регистрации {@link ru.sber.entities.Employee сотрудника}
 */
@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String employeeName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Position position;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 5)
    private String branchOffice;
}
