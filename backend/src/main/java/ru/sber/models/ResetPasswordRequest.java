package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель настроек для запроса на сервис LeyCloak
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String type;
    private Boolean temporary;
    private String value;
}
