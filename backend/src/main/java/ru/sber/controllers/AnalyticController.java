package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.proxies.AnalyticProxy;

/**
 * Контроллер для взаимодействия с аналитикой по сервису Заказы
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("analytic")
public class AnalyticController {
    private final AnalyticProxy analyticProxy;

    @Autowired
    public AnalyticController(AnalyticProxy analyticProxy) {
        this.analyticProxy = analyticProxy;
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> getCountOrdersByEmployee() {
        log.info("Получает количество заказов сделанных работником");

        return analyticProxy.findCountOrdersByEmployee();
    }

    @GetMapping("/orders/per/month")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> getOrdersPerMonth(@RequestParam(required = false) Integer year,
                                               @RequestParam(required = false) Integer month) {
        log.info("Получает количество заказов поступивших за месяц");

        return analyticProxy.findOrdersPerMonth(year, month);
    }

    @GetMapping("/orders/per/year")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> getOrdersPerYear(@RequestParam Integer year) {
        log.info("Получает количество заказов поступивших за год");

        return analyticProxy.findOrdersPerYear(year);
    }
}
