package ru.sber.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.entities.enums.EStatusOrders;

import java.time.LocalDateTime;

/**
 * Заказ
 */

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long clientId;

    @Column(nullable = false)
    @Size(max = 20)
    private String clientName;

    @Column(nullable = false)
    @Size(max = 10)
    private int clientPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private EStatusOrders statusOrders;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deadline;
}
