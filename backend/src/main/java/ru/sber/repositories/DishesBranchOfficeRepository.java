package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Dish;
import ru.sber.entities.DishesBranchOffice;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для связи {@link ru.sber.entities.Dish блюд } и  {@link BranchOffice филиалов}
 */
@Repository
public interface DishesBranchOfficeRepository extends JpaRepository<DishesBranchOffice, Long> {
    boolean existsByBranchOffice_IdAndDish_Id(Long branchId, Long dishId);

    List<DishesBranchOffice> findByBranchOffice_Id(long id);

    List<DishesBranchOffice> findByBranchOffice_NameCity(String name);

    void deleteByDish_Id(long id);
}
