package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sber.entities.OrderToken;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.UserNotApproved;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.model.Order;
import ru.sber.proxies.OrderFeign;
import ru.sber.repositories.UserRepository;

import java.util.List;

/**
 * Реализует логику работы с order-service
 */
@Slf4j
@Service
public class OrderServiceImp implements OrderService {

    private final OrderFeign orderFeign;
    private final NotifyService notifyService;
    private final OrderTokenService orderTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImp(OrderFeign orderFeign,
                           NotifyService notifyService,
                           OrderTokenService orderTokenService,
                           JwtService jwtService,
                           UserRepository userRepository) {
        this.orderFeign = orderFeign;
        this.notifyService = notifyService;
        this.orderTokenService = orderTokenService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> updateOrderStatusById(Long id, Order order) {
        OrderToken orderToken = orderTokenService.getToken();
        var user = getUserJwtTokenSecurityContext();

        order.setBranchAddress(user.getBranchOffice().getAddress());
        order.setBranchId(user.getBranchOffice().getId());
        order.setEmployeeRestaurantId(user.getId());

        return orderFeign.updateOrderStatusById("Bearer " + orderToken.getAccessToken(), id, order);
    }

    @Override
    public ResponseEntity<?> cancelOrderById(Long id, Object massage) {
        OrderToken orderToken = orderTokenService.getToken();

        return orderFeign.cancelOrderById("Bearer " + orderToken.getAccessToken(), id, massage);
    }

    @Override
    public ResponseEntity<?> cancelOrderByListId(String listId, Object massage) {
        OrderToken orderToken = orderTokenService.getToken();

        return orderFeign.cancelOrderByListId("Bearer " + orderToken.getAccessToken(), listId, massage);
    }

    @Override
    public List<?> getListOrders() {
        log.info("Получает заказы для ресторана");
        var user = getUserJwtTokenSecurityContext();
        if (user.getStatus().equals(EStatusEmployee.UNDER_CONSIDERATION)) {
            throw new UserNotApproved("Пользователь не допущен к работе");
        }

        OrderToken orderToken = orderTokenService.getToken();

        return orderFeign.getListOrders("Bearer " + orderToken.getAccessToken()).getBody();
    }

    @Override
    public List<?> getListOrdersByNotify() {
        String strOrder = notifyService.getList();
        log.info("Обновляет информацию о заказах с id {}", strOrder);

        OrderToken orderToken = orderTokenService.getToken();

        if (!strOrder.isEmpty()) {
            return orderFeign.getListOrdersByNotify(
                    "Bearer " + orderToken.getAccessToken(), strOrder);
        }
        return List.of();
    }

    private User getUserJwtTokenSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            return userRepository.findById(jwtService.getSubClaim(jwtAuthenticationToken.getToken()))
                    .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
