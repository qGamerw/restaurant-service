package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель для взаимодействия пользователя с сервисом KeyCloak
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String idBranchOffice;
    private String firstName;
    private String lastName;
}