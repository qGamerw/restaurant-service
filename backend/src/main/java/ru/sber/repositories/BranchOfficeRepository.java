package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.BranchOffice;

/**
 * Репозиторий с {@link BranchOffice филиалами}
 */
@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long> {
}
