package ru.sber.proxies;

import org.springframework.http.ResponseEntity;

/**
 * Сервис для аналитических запросов к сервису Заказы
 */
public interface AnalyticProxy {
    /**
     * Определяет количество заказов принятых работником ресторана за все время
     *
     * @return количество заказов
     */
    ResponseEntity<?> findCountOrdersByEmployee();

    /**
     * Определяет количество заказов поступивших за месяц
     *
     * @param year  год
     * @param mouth месяц
     * @return количество заказов за месяц
     */
    ResponseEntity<?> findOrdersPerMonth(Integer year, Integer mouth);

    /**
     * Определяет количество заказов поступивших за год
     *
     * @param year год
     * @return количество заказов за год
     */
    ResponseEntity<?> findOrdersPerYear(Integer year);
}
