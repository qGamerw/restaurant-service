package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Уведомления о новом заказе
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idOrder;

    public Notify(Long idOrder) {
        this.idOrder = idOrder;
    }
}
