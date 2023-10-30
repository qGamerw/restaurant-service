package ru.sber.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Отвечает за взаимодействие с order-service
 */
@FeignClient(name = "orderService", url = "localhost:8083/")
public interface OrderFeign {
    @PutMapping("orders/{id}")
    ResponseEntity<?> updateOrderStatusById(@PathVariable Long id, @RequestBody Object status);

    @PutMapping("orders/{id}/cancel")
    ResponseEntity<?> cancelOrderById(@PathVariable Long id, @RequestBody Object massage);

    @GetMapping("orders")
    ResponseEntity<List<?>> getListOrders();
}