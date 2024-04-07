package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.controllers.OrderFeignController;

@Service
public class AnalyticServiceImpl implements AnalyticService {
    private final OrderFeignController orderFeignController;
    private final UserService userService;
    private final OrderTokenService orderTokenService;

    @Autowired
    public AnalyticServiceImpl(OrderFeignController orderFeignController,
                               UserService userService,
                               OrderTokenService orderTokenService) {
        this.orderFeignController = orderFeignController;
        this.userService = userService;
        this.orderTokenService = orderTokenService;
    }

    @Override
    public ResponseEntity<?> findCountOrdersByEmployee() {
        return orderFeignController.getCountOrderFromEmployeeRestaurant(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                userService.getUser().getId());
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        return orderFeignController.getOrderPerMonth(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                year,
                mouth);
    }

    @Override
    public ResponseEntity<?> findOrdersPerYear(Integer year) {
        return orderFeignController.getOrderPerYear(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                year);
    }
}
