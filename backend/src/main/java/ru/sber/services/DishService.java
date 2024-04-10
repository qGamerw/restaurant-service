package ru.sber.services;

import org.springframework.data.domain.Page;
import ru.sber.entities.Dish;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Dish блюдами}
 */
public interface DishService {

    /**
     * Получает все блюда в филиале
     *
     * @return Список блюд
     */
    List<Dish> getListDish();

    /**
     * Добавляет блюдо
     *
     * @param dish блюдо
     * @return Результат
     */
    long addDish(Dish dish);

    /**
     * Добавляет блюдо по имени
     *
     * @param name имя блюдо
     * @return Результат
     */
    Optional<String> addDishByName(String name);

    /**
     * Получает все блюда в городе
     *
     * @param name имя города
     * @return Список блюд
     */
    List<Dish> getListByNameCity(String name);

    /**
     * Получает все блюда по id
     *
     * @param listDishes list id
     * @return Список блюд
     */
    List<Dish> getListById(String listDishes);

    /**
     * Удаляет блюдо
     *
     * @param id id блюда
     * @return Результат
     */
    Optional<String> deleteDish(long id);

    /**
     * Обновляет блюдо
     *
     * @param dish блюдо
     * @return результат
     */
    Optional<String> updateDish(Dish dish);

    /**
     * Получает блюдо по id
     *
     * @param id id блюда
     * @return Блюдо
     */
    Optional<Dish> getDishById(long id);

    /**
     * Получает блюдо по страницам
     *
     * @param page номер страницы
     * @param size размер страницы
     * @return Страница
     */
    Page<Dish> getDishesByPage(int page, int size);
}
