package ru.sber.services;

import org.springframework.http.ResponseEntity;
import ru.sber.models.Order;

import java.util.List;

/**
 * Получает информацию о заказе
 */
public interface OrderService {
    /**
     * Обновляет статус заказа
     *
     * @param id    id заказа
     * @param order заказ
     * @return Результат
     */
    ResponseEntity<?> updateOrderStatusById(Long id, Order order);

    /**
     * Отменяет заказ
     *
     * @param id      id заказа
     * @param massage сообщение
     * @return Результат
     */
    ResponseEntity<?> cancelOrderById(Long id, Object massage);

    /**
     * Отменяет заказы
     *
     * @param listId listId заказов
     * @return Результат
     */
    ResponseEntity<?> cancelOrderByListId(String listId, Object massage);

    /**
     * Получает информацию о заказах
     *
     * @return Лист заказов
     */
    List<?> getListOrders();

    /**
     * Получает информацию о заказах после уведомления
     *
     * @return Лист заказов
     */
    List<?> getListOrdersByNotify();
}
