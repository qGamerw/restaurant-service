package ru.sber.services;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Получает информацию о заказе
 */
public interface OrderService {
    /**
     * Обновляет статус заказа
     *
     * @param id     id заказа
     * @param status статус
     */
    ResponseEntity<?> updateOrderStatusById(Long id, Object status);

    /**
     * Отменяет заказ
     *
     * @param id      id заказа
     * @param massage сообщение
     */
    ResponseEntity<?> cancelOrderById(Long id, Object massage);

    /**
     * Получает информацию о заказах
     */
    List<?> getListOrders();
}
