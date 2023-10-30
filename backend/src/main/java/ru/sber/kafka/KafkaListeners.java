package ru.sber.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {
    @KafkaListener(topics = "review_order", groupId = "selfMessages")
    void listenerReviewOrder(String data) {
        log.info("Listener получил: {}", data);
    }

    @KafkaListener(topics = "restaurant_status", groupId = "selfMessages")
    void listenerRestaurantStatus(String data) {
        log.info("Listener получил: {}", data);
    }
}
