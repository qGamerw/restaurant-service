package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сотрудник
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "employeeName"),
                @UniqueConstraint(columnNames = "email")
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 20)
    private String employeeName;

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

    @ManyToOne
    @JoinColumn(name = "employee_position", nullable = false)
    private Position position;

    public Employee(String employeeName, String email, String password) {
        this.employeeName = employeeName;
        this.email = email;
        this.password = password;
    }
}