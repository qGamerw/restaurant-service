package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entities.Order;
import ru.sber.entities.enums.EStatusOrders;

import java.util.List;

/**
 * Репозиторий с {@link Order заказами}
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusOrders(EStatusOrders statusOrders);
}
