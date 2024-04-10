package ru.sber.services;

import ru.sber.entities.BranchOffice;
import ru.sber.models.BranchOfficeLimit;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link BranchOffice филиалом}
 */
public interface BranchOfficeService {
    /**
     * Открывает или закрывает филиал
     *
     * @return Результат
     */
    Optional<String> openCloseBranchOffice();

    /**
     * Получает информацию о филиале
     *
     * @return Филиал
     */
    BranchOfficeLimit getBranchOfficeByEmployee();

    /**
     * Получает информацию о филиалах
     *
     * @return Список филиалов
     */
    List<BranchOfficeLimit> getListBranchOffice();
}
