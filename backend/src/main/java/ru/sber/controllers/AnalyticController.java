package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.services.AnalyticService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("analytic")
public class AnalyticController {
    private final AnalyticService analyticService;

    @Autowired
    public AnalyticController(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }

    @GetMapping("/employee")
    public ResponseEntity<?> getCountOrderFromEmployeeRestaurant() {
        log.info("Получает количество заказов сделанных работником ресторана");

        return analyticService.findCountOrderFromEmployeeRestaurantId();
    }

    @GetMapping("/orders/per/month")
    public ResponseEntity<?> getOrderPerMonth(@RequestParam Integer year,
                                                 @RequestParam Integer month) {
        log.info("Получает количество заказов поступивших за месяц");

        return analyticService.findOrdersPerMonth(year, month);
    }
}
