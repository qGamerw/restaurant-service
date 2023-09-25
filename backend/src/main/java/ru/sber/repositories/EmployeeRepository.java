package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Employee;
import ru.sber.entities.enums.EPosition;

import java.util.Optional;

/**
 * Репозиторий с {@link Employee сотрудниками}
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeName(String name);

    boolean existsByEmail(String email);

    Optional<Employee> findByEmployeeName(String name);

    boolean existsByIdAndPosition_Position(long id, EPosition ePosition);
}
