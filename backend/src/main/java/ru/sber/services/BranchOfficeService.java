package ru.sber.services;

import ru.sber.entities.BranchOffice;

import java.util.List;

/**
 * Сервис для взаимодействия с {@link BranchOffice филиалом}
 */
public interface BranchOfficeService {
    /**
     * Закрывает филиал
     *
     * @return boolean
     */
    boolean closeBranchOffice();

    /**
     * Открывает филиал
     *
     * @return boolean
     */
    boolean openBranchOffice();

    /**
     * Получает информацию о филиале
     *
     * @return BranchOffice
     */
    BranchOffice getBranchOfficeByEmployee();

    /**
     * Получает информацию о филиалах
     *
     * @return List<BranchOffice>
     */
    List<BranchOffice> getListBranchOffice();
}
