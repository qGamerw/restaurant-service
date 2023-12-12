package ru.sber.entities.response;

import lombok.Data;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Role;
import ru.sber.entities.User;

/**
 * Класс для вывода информации при входе {@link User сотрудника}
 */
@Data
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String employeeName;
    private String email;
    private BranchOffice branchOffice;
    private Role role;

    public JwtResponse(String accessToken, Long id, String employeeName, String email, BranchOffice branchOffice, Role role) {
        this.accessToken = accessToken;
        this.id = id;
        this.employeeName = employeeName;
        this.email = email;
        this.branchOffice = branchOffice;
        this.role = role;
    }
}