package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entities.Role;
import ru.sber.entities.enums.ERole;

import java.util.Optional;

/**
 * Репозиторий с {@link Role должностями}
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
