package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Employee;
import ru.sber.model.EmployeeLimit;
import ru.sber.services.EmployeeService;

import java.util.Optional;

/**
 * Контроллер для взаимодействия {@link Employee сотрудником}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/info")
    public ResponseEntity<EmployeeLimit> getUserById() {
        log.info("Выводит данные о сотруднике");

        Optional<EmployeeLimit> employee = employeeService.getEmployeeById();

        return employee.map(
                value -> ResponseEntity
                        .ok()
                        .body(value))
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable long id) {
        log.info("Удаление аккаунта сотрудника по id {}", id);

        var isDeleted = employeeService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}