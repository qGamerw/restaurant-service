package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.entities.enums.ERole;

/**
 * Должность у {@link User сотрудника}
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    @NotBlank
    private ERole role;
}