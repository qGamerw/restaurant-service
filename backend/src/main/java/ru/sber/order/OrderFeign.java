package ru.sber.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("orders/cancel")
    ResponseEntity<?> cancelOrderByListId(@RequestParam String listId, @RequestBody Object message);

    @GetMapping("orders")
    ResponseEntity<List<?>> getListOrders();

    @GetMapping("orders/orders/notify/{orderId}")
    List<?> getListOrdersByNotify(@PathVariable String orderId);

    @GetMapping("analytic/employee/{id}")
    public ResponseEntity<Integer> getCountOrderFromEmployeeRestaurant(@PathVariable("id") long idEmployeeRestaurant);

    @GetMapping("analytic/orders/per/month")
    public ResponseEntity<Long> getOrderPerMonth(@RequestParam(required = false) Integer year,
                                                 @RequestParam(required = false) Integer month);
}