package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.OrderToken;
import ru.sber.exceptions.OrderTokenNotFoundException;
import ru.sber.repositories.OrderTokenRepository;

@Slf4j
@Service
public class OrderTokenServiceImpl implements OrderTokenService {
    private final OrderTokenRepository orderTokenRepository;

    @Autowired
    public OrderTokenServiceImpl(OrderTokenRepository orderTokenRepository) {
        this.orderTokenRepository = orderTokenRepository;
    }
    
    @Override
    public boolean save(OrderToken orderToken) {
        log.info("Добавление в базу данных токена {}", orderToken.getAccessToken());

        orderTokenRepository.save(orderToken);
        return true;
    }
    
    @Override
    public OrderToken getToken() {
        log.info("Получение токена из базы данных");

        return orderTokenRepository.findById(1)
                .orElseThrow(() -> new OrderTokenNotFoundException("Токен не найден в базе данных"));
    }
}
