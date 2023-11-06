package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.entities.Notify;
import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;
import ru.sber.repositories.NotifyRepository;
import ru.sber.repositories.PositionRepository;

import java.util.Optional;

@Slf4j
@Service
public class NotifyServiceImp implements NotifyService {
    private final NotifyRepository notifyRepository;

    public NotifyServiceImp(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    @Override
    public long addNotify(Long id) {
        var isNotify = notifyRepository.existsByIdOrder(id);
        log.info("Добавляет в активные уведомления {}", isNotify);

        if (isNotify){
            return id;
        } else {
            return notifyRepository.save(new Notify(id)).getId();
        }
    }
}
