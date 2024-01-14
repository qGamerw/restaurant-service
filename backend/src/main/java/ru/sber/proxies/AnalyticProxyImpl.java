package ru.sber.proxies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.services.UserService;

@Service
@Slf4j
public class AnalyticProxyImpl implements AnalyticProxy {
    private final OrderFeign orderFeign;
    private final UserService userService;
    private final KeyCloakProxy keyCloakProxy;

    @Autowired
    public AnalyticProxyImpl(OrderFeign orderFeign,
                             UserService userService,
                             KeyCloakProxy keyCloakProxy) {
        this.orderFeign = orderFeign;
        this.userService = userService;
        this.keyCloakProxy = keyCloakProxy;
    }

    @Override
    public ResponseEntity<?> findCountOrdersByEmployee() {
        var user = userService.getUser();

        log.info("Ищет количество заказов у сотрудника с id {}", user.getId());

        String orderToken = keyCloakProxy.checkingValidityOfToken();
        return orderFeign.getCountOrderFromEmployeeRestaurant("Bearer " + orderToken, user.getId());
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        log.info("Ищет количество заказов за месяц начиная с год(а) {}, месяц(а) {}", year, mouth);

        String orderToken = keyCloakProxy.checkingValidityOfToken();
        return orderFeign.getOrderPerMonth("Bearer " + orderToken, year, mouth);
    }

    @Override
    public ResponseEntity<?> findOrdersPerYear(Integer year) {
        log.info("Ищет количество заказов за год: {}", year);

        String orderToken = keyCloakProxy.checkingValidityOfToken();
        return orderFeign.getOrderPerYear("Bearer " + orderToken, year);
    }
}
