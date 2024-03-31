package ru.sber.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Токены для авторизации в сервисе заказов
 */
@Entity
@Table(name = "order_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderToken {
    @Id
    private Integer id;

    @Column(length = 10000)
    private String accessToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime tokenExpiration;

    public OrderToken(String accessToken) {
        this.id = 1;
        this.accessToken = accessToken;
        this.tokenExpiration = LocalDateTime.now().plusMinutes(14);
    }
}
