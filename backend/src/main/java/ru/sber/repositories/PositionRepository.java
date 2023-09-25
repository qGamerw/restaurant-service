package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;

import java.util.Optional;

/**
 * Репозиторий с {@link Position должностями}
 */
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPosition(EPosition position);
}
