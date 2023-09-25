package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;
import ru.sber.repositories.PositionRepository;

import java.util.Optional;

@Slf4j
@Service
public class PositionServiceImp implements PositionService {
    private final PositionRepository positionRepository;

    public PositionServiceImp(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Optional<Position> findByName(EPosition ePosition) {
        log.info("Ищет должность {}", ePosition);

        return positionRepository.findByPosition(ePosition);
    }
}
