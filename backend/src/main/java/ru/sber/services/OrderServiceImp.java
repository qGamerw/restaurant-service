package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final NotifyService notifyService;

    @Autowired
    public OrderServiceImp(OrderFeign orderFeign, NotifyService notifyService) {
        this.orderFeign = orderFeign;
        this.notifyService = notifyService;
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
    public ResponseEntity<?> cancelOrderByListId(String listId, Object massage) {
        return orderFeign.cancelOrderByListId(listId, massage);
    }

    @Override
    public List<?> getListOrders() {
        return orderFeign.getListOrders().getBody();
    }

    @Override
    public List<?> getListOrdersByNotify() {
        String strOrder = notifyService.getList();
        log.info("Обновляет информацию о заказах с id {}", strOrder);

        if (!strOrder.isEmpty()) {
            return orderFeign.getListOrdersByNotify(strOrder);
        }
        return List.of();
    }
}
