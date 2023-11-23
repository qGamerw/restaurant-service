package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.entities.Notify;
import ru.sber.repositories.NotifyRepository;

import java.util.List;

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
        log.info("Добавляет в активные уведомления false={}", isNotify);

        if (isNotify) {
            return id;
        } else {
            return notifyRepository.save(new Notify(id)).getId();
        }
    }

    @Override
    public String getList() {
        List<Notify> listOrder = notifyRepository.findAll();

        if (!listOrder.isEmpty()) {
            notifyRepository.deleteAll();
            return listOrder.stream()
                    .map(item -> item.getIdOrder().toString())
                    .reduce("", (s1, s2) -> s1.isEmpty() ? s2 : s1 + "," + s2);
        }

        return "";
    }
}
