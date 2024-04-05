package ru.sber.messageBrokers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sber.models.Order;
import ru.sber.services.NotifyServiceImp;

/**
 * Класс для чтения информации из Kafka
 */
@Slf4j
@Component
public class KafkaListeners {
    private final NotifyServiceImp notifyServiceImp;

    public KafkaListeners(NotifyServiceImp notifyServiceImp) {
        this.notifyServiceImp = notifyServiceImp;
    }

    /**
     * Читает информацию из темы review_order в Kafka
     *
     * @param data прочитанные данные
     */
    @KafkaListener(topics = "review_order", groupId = "selfMessages")
    void listenerReviewOrder(String data) {
        //TODO: уже не помню зачем он нужен.
        log.info("Listener получил: {}", data);
    }

    /**
     * Читает информацию из темы restaurant_status в Kafka
     *
     * @param data прочитанные данные
     */
    @KafkaListener(topics = "restaurant_status", groupId = "selfMessages")
    void listenerRestaurantStatus(String data) {
        try {
            Order order = new ObjectMapper().readValue(data, Order.class);
            log.info("Order: {}", order);
            notifyServiceImp.addNotify(order.getId());
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error("Ошибка чтения: Не удалось прочитать полученные данные.");
        }
    }
}
