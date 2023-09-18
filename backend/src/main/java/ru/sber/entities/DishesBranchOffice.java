package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Блюда в каждом филиале
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "orders_dishes_branchs_office",
            joinColumns = @JoinColumn(name = "dishes_branchs_office_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Dish> dishes = new HashSet<>();

    public DishesBranchOffice(Dish dish, BranchOffice branchOffice, Set<Dish> dishes) {
        this.dish = dish;
        this.branchOffice = branchOffice;
        this.dishes = dishes;
    }
}
