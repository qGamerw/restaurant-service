package ru.sber.model;


import lombok.Data;

/**
 * Класс для отправки ошибки при запросе
 */
@Data
public class ErrorDetails {
    private String message;
}
