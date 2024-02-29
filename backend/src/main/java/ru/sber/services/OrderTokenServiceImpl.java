package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.OrderToken;
import ru.sber.proxies.KeyCloakProxy;
import ru.sber.repositories.OrderTokenRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderTokenServiceImpl implements OrderTokenService {
    private final OrderTokenRepository orderTokenRepository;
    private final KeyCloakProxy keyCloakProxy;

    @Autowired
    public OrderTokenServiceImpl(OrderTokenRepository orderTokenRepository,
                                 KeyCloakProxy keyCloakProxy) {
        this.orderTokenRepository = orderTokenRepository;
        this.keyCloakProxy = keyCloakProxy;
    }

    @Override
    public OrderToken getToken() {
        log.info("Получение токена из базы данных");

        OrderToken orderTokens = orderTokenRepository.findById(1).orElse(null);

        if (orderTokens == null || !LocalDateTime.now().isBefore(orderTokens.getTokenExpiration())){
            return keyCloakProxy.updateOrderToken();
        }
        return orderTokens;
    }
}
