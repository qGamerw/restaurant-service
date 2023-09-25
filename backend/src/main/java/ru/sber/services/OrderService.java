package ru.sber.services;

import ru.sber.entities.Order;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Order заказом}
 */
public interface OrderService {
    /**
     * Добавляет заказ
     *
     * @param order заказ
     * @return boolean
     */
    boolean addOrder(Order order);

    /**
     * Обновляет заказ
     *
     * @param order заказ
     * @return boolean
     */
    boolean updateOrder(Order order);

    /**
     * Получает заказ все заказы которые стоят в очереди на рассмотрение
     *
     * @return List<Order>
     */
    List<Order> getListOrder();

    /**
     * Получает заказ по id
     *
     * @param id id заказа
     * @return Optional<Order>
     */
    Optional<Order> getOrderById(long id);
}
