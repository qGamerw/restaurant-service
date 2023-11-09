package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderStatusById(@PathVariable Long id, @RequestBody Object status) {
        log.info("Обновляет статус у заказа {}", id);

        return orderService.updateOrderStatusById(id, status);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable Long id, @RequestBody Object massage) {
        log.info("Отменяет заказа с {}", id);

        return orderService.cancelOrderById(id, massage);
    }

    @GetMapping
    public ResponseEntity<List<?>> getListOrders() {
        log.info("Получает заказы");

        return ResponseEntity.ok().body(orderService.getListOrders());
    }

    @GetMapping("/notify")
    public ResponseEntity<List<?>> getListOrdersByNotify() {
        log.info("Получает заказы для уведомления");

        List<?> orders = orderService.getListOrdersByNotify();

        log.info("{}", orders);
        return ResponseEntity.ok().body(orders);
    }
}
