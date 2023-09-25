package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Category;

/**
 * Репозиторий с {@link Category категориями}
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
