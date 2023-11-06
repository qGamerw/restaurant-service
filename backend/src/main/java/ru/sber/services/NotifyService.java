package ru.sber.services;

import ru.sber.entities.Dish;
import ru.sber.entities.Notify;
import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;

import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Position должностью}
 */
public interface NotifyService {
    long addNotify(Long id);
}

