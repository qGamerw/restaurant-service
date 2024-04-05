package ru.sber.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.entities.BranchOffice;

import java.util.List;

/**
 * Модель для вывода неполной информации о пользователе
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDetails {
    private String username;
    private String email;
    private List<String> role;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private BranchOffice idBranchOffice;
    private String status;
}
