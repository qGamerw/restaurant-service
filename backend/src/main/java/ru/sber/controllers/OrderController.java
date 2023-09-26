package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Order;
import ru.sber.services.OrderService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для взаимодействия {@link Order заказами}
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

    @PostMapping
    public ResponseEntity<Long> addOrder(@RequestBody Order order) {
        log.info("Добавляет заказ по id {}", order.getId());

        return ResponseEntity
                .created(URI.create("orders/" + orderService.addOrder(order)))
                .build();
    }

    @PutMapping
    public ResponseEntity<Long> updateOrder(@RequestBody Order order) {
        log.info("Обновляет заказ по id {}", order.getId());

        var isUpdate = orderService.updateOrder(order);

        if (isUpdate){
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getListOrder() {
        log.info("Получает заказы из очереди на рассмотрение");

        List<Order> orders = orderService.getListOrder();

        return ResponseEntity.ok()
                .body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getCategoryById(@PathVariable long id) {
        log.info("Получает заказ по id");

        Optional<Order> order = orderService.getOrderById(id);

        return order.map(
                        value -> ResponseEntity
                                .ok()
                                .body(value))
                        .orElseGet(() -> ResponseEntity
                                .notFound()
                                .build());
    }
}
