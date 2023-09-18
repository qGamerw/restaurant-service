package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Сотрудник
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 20)
    private String name;

    @Column(nullable = false)
    @Size(max = 50)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(max = 120)
    private String password;

    @ManyToOne
    @JoinColumn(name = "branchs_office_id", nullable = false)
    private BranchOffice branchOffice;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_positions",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> roles = new HashSet<>();

    public Employee(String name, String email, String password, BranchOffice branchOffice, Set<Position> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.branchOffice = branchOffice;
        this.roles = roles;
    }
}