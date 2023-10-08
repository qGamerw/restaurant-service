package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Блюдо для {@link BranchOffice филиала}
 */

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "dishes_branchs_office")
public class DishesBranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "branch_office_id", nullable = false)
    private BranchOffice branchOffice;

    public DishesBranchOffice(Dish dish, BranchOffice branchOffice) {
        this.dish = dish;
        this.branchOffice = branchOffice;
    }
}
