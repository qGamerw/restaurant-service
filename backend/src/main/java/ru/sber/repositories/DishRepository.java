package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Dish;

/**
 * Репозиторий для взаимодействия с {@link Dish блюдами}
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    /**
     * Проверяет есть ли блюдо в базе данных по имени
     *
     * @param name имя блюда
     * @return Результат
     */
    boolean existsByName(String name);

    /**
     * Ищет блюдо по имени
     *
     * @param name имя блюда
     * @return Блюдо
     */
    Dish findByName(String name);
}
