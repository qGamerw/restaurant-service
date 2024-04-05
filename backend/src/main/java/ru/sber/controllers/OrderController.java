package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.models.Order;
import ru.sber.services.OrderService;

import java.util.List;

/**
 * Получает Rest запросы к сервису заказов
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Обновляет статус заказа
     *
     * @param id    id заказа
     * @param order новый статус заказа
     * @return результат
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderStatusById(@PathVariable Long id, @RequestBody Order order) {
        log.info("Обновляет статус у заказа {}", id);

        return orderService.updateOrderStatusById(id, order);
    }

    /**
     * Обновляет статус заказа
     *
     * @param id      id заказа
     * @param massage причина отмены
     * @return результат
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable Long id, @RequestBody Object massage) {
        log.info("Отменяет заказа с {}", id);

        return orderService.cancelOrderById(id, massage);
    }

    /**
     * Обновляет статус заказа
     *
     * @param listId  список заказов
     * @param message причина отмены
     * @return результат
     */
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrderByListId(@RequestParam String listId, @RequestBody Object message) {
        log.info("Отменяет заказы с {}", listId);

        return orderService.cancelOrderByListId(listId, message);
    }

    /**
     * Получает заказы
     *
     * @return результат
     */
    @GetMapping
    public ResponseEntity<List<?>> getListOrders() {
        log.info("Получает заказы");

        return ResponseEntity
                .ok()
                .body(orderService.getListOrders());
    }

    /**
     * Получает заказы по уведомлению
     *
     * @return результат
     */
    @GetMapping("/notify")
    public ResponseEntity<List<?>> getListOrdersByNotify() {
        log.info("Получает заказы для уведомления");

        return ResponseEntity
                .ok()
                .body(orderService.getListOrdersByNotify());
    }
}
