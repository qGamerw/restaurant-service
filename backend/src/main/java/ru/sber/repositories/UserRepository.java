package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;

import java.util.Optional;

/**
 * Репозиторий с {@link User сотрудниками}
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String idUser);

    int countByBranchOffice_IdAndStatus(long idBranchOffice, EStatusEmployee statusEmployee);

    boolean deleteById(String id);
}
