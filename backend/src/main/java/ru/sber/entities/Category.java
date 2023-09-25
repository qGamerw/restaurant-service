package ru.sber.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.entities.enums.ECategory;

/**
 * Категория {@link Dish блюда}
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECategory category;

    public Category(ECategory category) {
        this.category = category;
    }
}
