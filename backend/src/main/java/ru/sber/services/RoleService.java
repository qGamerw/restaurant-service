package ru.sber.services;

import ru.sber.entities.Role;
import ru.sber.entities.enums.ERole;

import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Role должностью}
 */
public interface RoleService {

    /**
     * Ищет должность по имени
     *
     * @param position должность
     * @return Optional<Position>
     */
    Optional<Role> findByRole(ERole position);
}
