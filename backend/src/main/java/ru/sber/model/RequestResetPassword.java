package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель для отправки данных на сервис LeyCloak
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResetPassword {
    private String type;
    private Boolean temporary;
    private String value;
}
