package ru.sber.services;

import ru.sber.entities.Category;
import ru.sber.entities.DishesBranchOffice;

import java.util.List;

/**
 * Сервис для взаимодействия с {@link Category категорией}
 */
public interface DishBranchOfficeService {
    /**
     * Получает все блюда по филиалу
     *
     * @return List<Category>
     */
    List<DishesBranchOffice> getListDishBranchOffice();

    /**
     * Получает все блюда по филиалу
     *
     * @return List<Category>
     */
    List<DishesBranchOffice> getListDishBranchOfficeAll();

}
