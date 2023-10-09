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
        return orderService.updateOrderStatusById(id, status);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable Long id, @RequestBody Object massage) {
        return orderService.cancelOrderById(id, massage);
    }

    @GetMapping
    public ResponseEntity<List<?>> getListOrders() {
        return ResponseEntity.ok().body(orderService.getListOrders());
    }
}
