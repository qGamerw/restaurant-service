package ru.sber.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Филиал ресторана
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "branchs_office",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "address")
        })
public class BranchOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 20)
    private String address;

    @Column(nullable = false)
    @Size(max = 50)
    private String status;

    @Column(nullable = false)
    @Size(max = 50)
    private String nameCity;

    public BranchOffice(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public BranchOffice(Long id) {
        this.id = id;
    }
}