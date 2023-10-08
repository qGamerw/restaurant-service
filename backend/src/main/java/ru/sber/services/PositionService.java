package ru.sber.services;

import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;

import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Position должностью}
 */
public interface PositionService {

    /**
     * Ищет должность по имени
     *
     * @param position должность
     * @return Optional<Position>
     */
    Optional<Position> findByName(EPosition position);
}
