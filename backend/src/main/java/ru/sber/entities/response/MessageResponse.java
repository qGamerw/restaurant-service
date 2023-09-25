package ru.sber.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Ответ на сообщение
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
