package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sber.exceptions.UserNotFound;
import ru.sber.order.OrderFeign;

@Service
@Slf4j
public class AnalyticServiceImpl implements AnalyticService {

    private final OrderFeign orderFeign;
    private final JwtService jwtService;

    public AnalyticServiceImpl(OrderFeign orderFeign, JwtService jwtService) {
        this.orderFeign = orderFeign;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> findCountOrderFromEmployeeRestaurantId() {
        log.info("{}", getUserId());
        return orderFeign.getCountOrderFromEmployeeRestaurant(getUserId());
    }

    @Override
    public ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth) {
        return orderFeign.getOrderPerMonth(
                year == 0 ? null : year,
                mouth == 0 ? null : mouth);
    }

    private String getUserId() {
        log.info("Получает id сотрудника текущей сессии");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            Jwt jwt = jwtAuthenticationToken.getToken();
            String subClaim = jwtService.getSubClaim(jwt);

            return subClaim;
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }
}
