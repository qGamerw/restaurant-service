package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.order.OrderFeign;

import java.util.List;

/**
 * Реализует логику работы с order-service
 */
@Slf4j
@Service
public class OrderServiceImp implements OrderService {

    private final OrderFeign orderFeign;

    public OrderServiceImp(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
    }

    @Override
    public ResponseEntity<?> updateOrderStatusById(Long id, Object status) {
        return orderFeign.updateOrderStatusById(id, status);
    }

    @Override
    public ResponseEntity<?> cancelOrderById(Long id, Object massage) {
        return orderFeign.cancelOrderById(id, massage);
    }

    @Override
    public List<?> getListOrders() {
        return orderFeign.getListOrders().getBody();
    }
}
