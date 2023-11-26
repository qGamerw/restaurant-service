package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.entities.enums.EStatusOrders;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.order.OrderFeign;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.time.LocalDate;

@Service
@Slf4j
public class AnalyticServiceImpl implements AnalyticService {

    private final OrderFeign orderFeign;

    public AnalyticServiceImpl(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
    }

    @Override
    public ResponseEntity<?> findCountOrderFromEmployeeRestaurantId() {
        return orderFeign.getCountOrderFromEmployeeRestaurant(getEmployeeId());
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        return orderFeign.getOrderPerMonth(
                year == 0? null: year,
                mouth == 0? null: mouth);
    }

    private long getEmployeeId() {
        log.info("Получает id сотрудника текущей сессии");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getId();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
