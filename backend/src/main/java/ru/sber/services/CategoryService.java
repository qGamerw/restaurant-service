package ru.sber.services;

import ru.sber.entities.Category;

import java.util.List;

/**
 * Сервис для взаимодействия с {@link Category категорией}
 */
public interface CategoryService {
    /**
     * Получает все категории
     *
     * @return List<Category>
     */
    List<Category> getListCategory();
}
