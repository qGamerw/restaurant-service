package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.entities.OrderToken;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.UserNotApproved;
import ru.sber.models.Order;
import ru.sber.controllers.OrderFeignController;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImp implements OrderService {

    private final OrderFeignController orderFeignController;
    private final NotifyService notifyService;
    private final OrderTokenService orderTokenService;
    private final UserService userService;

    @Autowired
    public OrderServiceImp(OrderFeignController orderFeignController,
                           NotifyService notifyService,
                           OrderTokenService orderTokenService,
                           UserService userService) {
        this.orderFeignController = orderFeignController;
        this.notifyService = notifyService;
        this.orderTokenService = orderTokenService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> updateOrderStatusById(Long id, Order order) {
        OrderToken orderToken = orderTokenService.getToken();

        var user = userService.getUser();
        order.setBranchAddress(user.getBranchOffice().getAddress());
        order.setBranchId(user.getBranchOffice().getId());
        order.setEmployeeRestaurantId(user.getId());

        return orderFeignController.updateOrderStatusById(
                "Bearer " + orderToken.getAccessToken(),
                id,
                order);
    }

    @Override
    public ResponseEntity<?> cancelOrderById(Long id, Object massage) {
        return orderFeignController.cancelOrderById(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                id,
                massage);
    }

    @Override
    public ResponseEntity<?> cancelOrderByListId(String listId, Object massage) {
        return orderFeignController.cancelOrderByListId(
                "Bearer " + orderTokenService.getToken().getAccessToken(),
                listId,
                massage);
    }

    @Override
    public List<?> getListOrders() {
        try {
            if (userService.getUser().getStatus().equals(EStatusEmployee.UNDER_CONSIDERATION)) {
                throw new UserNotApproved("Пользователь не допущен к работе");
            }

            OrderToken orderToken = orderTokenService.getToken();
            return orderFeignController.getListOrders("Bearer " + orderToken.getAccessToken()).getBody();
        } catch (UserNotApproved e) {
            log.error("{}", e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<?> getListOrdersByNotify() {
        try {
            String listOrder = notifyService.getListId();
            OrderToken orderToken = orderTokenService.getToken();

            if (listOrder.isEmpty()) {
                return List.of();
            } else {
                return orderFeignController.getListOrdersByNotify(
                        "Bearer " + orderToken.getAccessToken(),
                        listOrder);
            }
        } catch (UserNotApproved e) {
            log.error("{}", e.getMessage());
            return List.of();
        }
    }
}
