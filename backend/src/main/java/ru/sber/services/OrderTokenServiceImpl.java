package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.OrderToken;
import ru.sber.proxies.AuthProxy;
import ru.sber.repositories.OrderTokenRepository;

import java.time.LocalDateTime;

@Service
public class OrderTokenServiceImpl implements OrderTokenService {
    private final OrderTokenRepository orderTokenRepository;
    private final AuthProxy authProxy;

    @Autowired
    public OrderTokenServiceImpl(OrderTokenRepository orderTokenRepository,
                                 AuthProxy authProxy) {
        this.orderTokenRepository = orderTokenRepository;
        this.authProxy = authProxy;
    }

    @Override
    public OrderToken getToken() {
        OrderToken orderTokens = orderTokenRepository.findById(1).orElse(null);

        if (orderTokens == null || LocalDateTime.now().isAfter(orderTokens.getTokenExpiration())) {
            authProxy.updateOrderToken();
            return orderTokenRepository.findById(1).get();
        }
        return orderTokens;
    }
}
