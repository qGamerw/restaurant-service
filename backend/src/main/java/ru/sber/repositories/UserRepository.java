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
    /**
     * Находит пользователя по id
     *
     * @param idUser id пользователя
     * @return пользователь
     */
    Optional<User> findById(String idUser);

    /**
     * Считает количество пользователей со статусом в филиале
     *
     * @param idBranchOffice id филиала
     * @param statusEmployee статус
     * @return количество сотрудников
     */
    int countByBranchOffice_IdAndStatus(long idBranchOffice, EStatusEmployee statusEmployee);

    /**
     * Удаление пользователя по id
     *
     * @param id id пользователя
     * @return сколько записей удалено
     */
    long deleteById(String id);
}
