package ru.sber.model;

import lombok.Data;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Employee;
import ru.sber.entities.Position;

/**
 * Обрезанный {@link ru.sber.entities.Employee сотрудник} для вывода информации из БД
 */
@Data
public class EmployeeLimit {
    private String name;
    private String email;
    private BranchOffice branchOffice;
    private Position position;

    public EmployeeLimit(Employee employee) {
        this.name = employee.getEmployeeName();
        this.email = employee.getEmail();
        this.branchOffice = employee.getBranchOffice();
        this.position = employee.getPosition();
    }
}