package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Notify;

/**
 * Репозиторий для взаимодействия с {@link Notify уведомлениями о заказе}
 */
@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {
    /**
     * Проверяет есть ли заказ в уведомлениях по id
     *
     * @param id id заказа
     * @return Результат
     */
    boolean existsByIdOrder(Long id);
}
