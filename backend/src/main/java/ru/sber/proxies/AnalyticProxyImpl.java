package ru.sber.proxies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.services.OrderTokenService;
import ru.sber.services.UserService;

@Service
public class AnalyticProxyImpl implements AnalyticProxy {
    private final OrderFeign orderFeign;
    private final UserService userService;
    private final OrderTokenService orderTokenService;

    @Autowired
    public AnalyticProxyImpl(OrderFeign orderFeign,
                             UserService userService,
                             OrderTokenService orderTokenService) {
        this.orderFeign = orderFeign;
        this.userService = userService;
        this.orderTokenService = orderTokenService;
    }

    @Override
    public ResponseEntity<?> findCountOrdersByEmployee() {
        return orderFeign.getCountOrderFromEmployeeRestaurant(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                userService.getUser().getId());
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        return orderFeign.getOrderPerMonth(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                year,
                mouth);
    }

    @Override
    public ResponseEntity<?> findOrdersPerYear(Integer year) {
        return orderFeign.getOrderPerYear(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                year);
    }
}
