package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Dish;

import java.util.List;

/**
 * Репозиторий с {@link Dish блюдами}
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    boolean existsByName(String name);

    Dish findByName(String name);
}
