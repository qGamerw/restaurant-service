package ru.sber.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Employee;
import ru.sber.repositories.EmployeeRepository;

@Slf4j
@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String employeeName) throws UsernameNotFoundException {
        log.info("Поиск сотрудника по имени в БД");
        Employee employee = employeeRepository.findByEmployeeName(employeeName)
                .orElseThrow(() -> new UsernameNotFoundException("Сотрудник с логином : "
                        + employeeName + " не найден"));

        return EmployeeDetailsImpl.build(employee);
    }
}
