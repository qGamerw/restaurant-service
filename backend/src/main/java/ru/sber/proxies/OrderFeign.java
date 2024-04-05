package ru.sber.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.models.Order;

import java.util.List;

/**
 * Класс для взаимодействия с сервисом заказы
 */
@FeignClient(name = "orderService", url = "localhost:8083/")
public interface OrderFeign {
    /**
     * Обновляет статус заказа по id
     *
     * @param bearerToken заголовок
     * @param id          id заказа
     * @param order       статус заказа
     * @return Результат
     */
    @PutMapping("orders/{id}")
    ResponseEntity<?> updateOrderStatusById(@RequestHeader("Authorization") String bearerToken,
                                            @PathVariable Long id,
                                            @RequestBody Order order);

    /**
     * Отменяет заказ по id
     *
     * @param bearerToken заголовок
     * @param id          id заказа
     * @param massage     сообщение
     * @return Результат
     */
    @PutMapping("orders/{id}/cancel")
    ResponseEntity<?> cancelOrderById(@RequestHeader("Authorization") String bearerToken,
                                      @PathVariable Long id,
                                      @RequestBody Object massage);

    /**
     * Отменяет заказы по id
     *
     * @param bearerToken заголовок
     * @param listId      id заказов
     * @param message     статус заказа
     * @return Результат
     */
    @PutMapping("orders/cancel")
    ResponseEntity<?> cancelOrderByListId(@RequestHeader("Authorization") String bearerToken,
                                          @RequestParam String listId,
                                          @RequestBody Object message);

    /**
     * Получает заказы
     *
     * @param bearerToken заголовок
     * @return Результат
     */
    @GetMapping("orders")
    ResponseEntity<List<?>> getListOrders(@RequestHeader("Authorization") String bearerToken);

    /**
     * Получает заказы по id от уведомления
     *
     * @param bearerToken заголовок
     * @param orderId     id заказов
     * @return Результат
     */
    @GetMapping("orders/orders/notify/{orderId}")
    List<?> getListOrdersByNotify(@RequestHeader("Authorization") String bearerToken,
                                  @PathVariable String orderId);

    /**
     * Получает количество заказов по сотруднику
     *
     * @param bearerToken          заголовок
     * @param idEmployeeRestaurant id сотрудника
     * @return Результат
     */
    @GetMapping("analytic/employee/{id}")
    ResponseEntity<Integer> getCountOrderFromEmployeeRestaurant(@RequestHeader("Authorization") String bearerToken,
                                                                @PathVariable("id") String idEmployeeRestaurant);

    /**
     * Получает количество заказов за определенное время за месяц
     *
     * @param bearerToken заголовок
     * @param year        год
     * @param month       месяц
     * @return Результат
     */
    @GetMapping("analytic/orders/per/month")
    ResponseEntity<Long> getOrderPerMonth(@RequestHeader("Authorization") String bearerToken,
                                          @RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) Integer month);

    /**
     * Получает количество заказов за год
     *
     * @param bearerToken заголовок
     * @param year        год
     * @return Результат
     */
    @GetMapping("analytic/orders/per/year")
    ResponseEntity<List<?>> getOrderPerYear(@RequestHeader("Authorization") String bearerToken,
                                            @RequestParam Integer year);
}