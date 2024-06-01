package ru.sber;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.entities.OrderToken;
import ru.sber.proxies.AuthProxy;
import ru.sber.repositories.OrderTokenRepository;
import ru.sber.services.OrderTokenService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTokenServiceImplTest {

    @Autowired
    private OrderTokenService orderTokenService;

    @MockBean
    private OrderTokenRepository orderTokenRepository;

    @MockBean
    private AuthProxy authProxy;

    @Before
    public void setUp() {
        OrderToken orderToken = new OrderToken();
        orderToken.setId(1);
        orderToken.setTokenExpiration(LocalDateTime.now().minusHours(1));

        Mockito.when(orderTokenRepository.findById(1)).thenReturn(Optional.of(orderToken));
    }

    @Test
    public void testGetTokenExpiredToken() {
        OrderToken expectedOrderToken = new OrderToken();
        expectedOrderToken.setId(1);
        expectedOrderToken.setTokenExpiration(LocalDateTime.now().plusHours(1));

        Mockito.when(orderTokenRepository.findById(1)).thenReturn(Optional.of(expectedOrderToken));

        Mockito.when(authProxy.updateOrderToken()).thenAnswer(invocation -> {
            expectedOrderToken.setTokenExpiration(LocalDateTime.now().plusHours(1));
            return null;
        });

        OrderToken result = orderTokenService.getToken();
        assertEquals(expectedOrderToken, result);
    }
}
