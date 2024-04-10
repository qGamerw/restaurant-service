package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.OrderToken;

/**
 * Репозиторий для взаимодействия с {@link OrderToken токенами для авторизации в сервисе заказов}
 */
@Repository
public interface OrderTokenRepository extends JpaRepository<OrderToken, Integer> {
}
