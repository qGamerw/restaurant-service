package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Notify;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.UserNotApproved;
import ru.sber.repositories.NotifyRepository;

import java.util.List;

@Service
public class NotifyServiceImp implements NotifyService {
    private final NotifyRepository notifyRepository;
    private final UserService userService;

    @Autowired
    public NotifyServiceImp(
            NotifyRepository notifyRepository,
            UserService userService) {
        this.notifyRepository = notifyRepository;
        this.userService = userService;
    }

    @Override
    public void addNotify(Long id) {
        if (id != null & !notifyRepository.existsByIdOrder(id)) {
            notifyRepository.save(new Notify(id));
        }
    }

    @Override
    public String getListId() throws UserNotApproved {
        if (userService.getUser().getStatus().equals(EStatusEmployee.UNDER_CONSIDERATION)) {
            throw new UserNotApproved("Пользователь не допущен к работе");
        }

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
