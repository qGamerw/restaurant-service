package ru.sber.model;

import lombok.Data;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Role;
import ru.sber.entities.User;

/**
 * Обрезанный {@link User сотрудник} для вывода информации из БД
 */
@Data
public class UserLimit {
    private BranchOffice branchOffice;
    private Role role;

    public UserLimit(User user) {
        this.branchOffice = user.getBranchOffice();
        this.role = user.getRole();
    }
}