package ru.sber.services;

import ru.sber.entities.Employee;
import ru.sber.model.EmployeeLimit;

import java.util.Optional;

/**
 * Сервис для взаимодействия с {@link Employee сотрудником}
 */
public interface EmployeeService {
    /**
     * Проверяет на нахождении записи по name
     *
     * @param name имя
     * @return boolean
     */
    boolean existsByEmployeeName(String name);

    /**
     * Проверяет на нахождении записи по email
     *
     * @param email email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Сохраняет данные сотрудника
     *
     * @param employee сотрудник
     */
    void addEmployee(Employee employee);

    /**
     * Получает данные сотрудника по id
     *
     * @return Optional<EmployeeLimit>
     */
    Optional<EmployeeLimit> getEmployeeById();

    /**
     * Удаляет сотрудника по id
     *
     * @param id id сотрудника
     * @return boolean
     */
    boolean deleteById(long id);
}
