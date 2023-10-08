package ru.sber.entities.response;

import lombok.Data;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Position;

/**
 * Класс для вывода информации при входе {@link ru.sber.entities.Employee сотрудника}
 */
@Data
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String employeeName;
    private String email;
    private BranchOffice branchOffice;
    private Position position;

    public JwtResponse(String accessToken, Long id, String employeeName, String email, BranchOffice branchOffice, Position position) {
        this.accessToken = accessToken;
        this.id = id;
        this.employeeName = employeeName;
        this.email = email;
        this.branchOffice = branchOffice;
        this.position = position;
    }
}