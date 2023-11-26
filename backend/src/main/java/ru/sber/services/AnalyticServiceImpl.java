package ru.sber.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.entities.enums.EStatusOrders;
import ru.sber.order.OrderFeign;

import java.time.LocalDate;

@Service
public class AnalyticServiceImpl implements AnalyticService {

    private final OrderFeign orderFeign;

    public AnalyticServiceImpl(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
    }




    @Override
    public ResponseEntity<?> findCountOrderFromEmployeeRestaurantId(long employeeRestaurantId) {

        return orderFeign.getCountOrderFromEmployeeRestaurant(employeeRestaurantId);
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        return orderFeign.getOrderPerMonth(year, mouth);
    }
}
