package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Order;
import ru.sber.entities.enums.EStatusOrders;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.repositories.OrderRepository;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public boolean addOrder(Order order) {
        orderRepository.save(order);

        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        log.info("Обновляет заказ с id {}", order.getId());

        if (order.getEmployee().getBranchOffice().getId() == getBranchOfficeId()) {

            orderRepository.save(order);
            return true;
        }

        return false;
    }

    @Override
    public List<Order> getListOrder() {
        log.info("Получает список заказов");

        return orderRepository.findAllByStatusOrders(EStatusOrders.TODO)
                .stream()
                .filter(order -> order.getEmployee().getBranchOffice().getId() == getBranchOfficeId())
                .toList();
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        log.info("Получает заказ по id {}", id);

        return orderRepository.findById(id)
                .filter(order -> order.getEmployee().getBranchOffice().getId() == getBranchOfficeId());
    }

    private long getBranchOfficeId() {
        log.info("Получает id филиала");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getBranchOffice().getId();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
