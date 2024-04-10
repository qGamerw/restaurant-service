package ru.sber.services;

import ru.sber.entities.DishesBranchOffice;

import java.util.List;

/**
 * Сервис для взаимодействия с {@link DishesBranchOffice блюдами в филиале}
 */
public interface DishBranchOfficeService {
    /**
     * Получает все блюда по филиалу
     *
     * @return Список блюд
     */
    List<DishesBranchOffice> getListDishBranchOffice();

    /**
     * Получает все блюда по филиалу
     *
     * @return Список блюд
     */
    List<DishesBranchOffice> getListDishBranchOfficeAll();

}
