package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.entities.enums.EPosition;

/**
 * Должность у {@link Employee сотрудника}
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPosition position;

    public Position(EPosition position) {
        this.position = position;
    }
}