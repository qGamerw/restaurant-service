package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Employee;
import ru.sber.entities.enums.EPosition;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.model.EmployeeLimit;
import ru.sber.repositories.EmployeeRepository;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean existsByEmployeeName(String name) {
        return employeeRepository.existsByEmployeeName(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public void addEmployee(Employee employee) {
        log.info("Добавляет сотрудника");

        employeeRepository.save(employee);
    }

    @Override
    public Optional<EmployeeLimit> getEmployeeById() {
        log.info("Получает сотрудника по id");

        return employeeRepository.findById(getEmployeeId()).stream()
                .map(EmployeeLimit::new)
                .findAny();
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        log.info("Удаляет сотрудника по id");

        if (id == getEmployeeId() ||
                employeeRepository.existsByIdAndPosition_Position(getEmployeeId(), EPosition.POSITION_ADIN)) {
            employeeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private long getEmployeeId() {
        log.info("Получает id сотрудника текущей сессии");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getId();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
