package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.entities.DishesBranchOffice;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.repositories.DishesBranchOfficeRepository;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.util.List;

@Service
@Slf4j
public class DishBranchOfficeServiceImp implements DishBranchOfficeService {
    private final DishesBranchOfficeRepository dishesBranchOfficeRepository;

    public DishBranchOfficeServiceImp(DishesBranchOfficeRepository dishesBranchOfficeRepository) {
        this.dishesBranchOfficeRepository = dishesBranchOfficeRepository;
    }

    @Override
    public List<DishesBranchOffice> getListDishBranchOffice() {
        return dishesBranchOfficeRepository.findByBranchOffice_Id(getBranchOfficeId());
    }

    @Override
    public List<DishesBranchOffice> getListDishBranchOfficeAll() {
        return dishesBranchOfficeRepository.findAll();
    }

    private long getBranchOfficeId() {
        log.info("Получает id филиала");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getBranchOffice().getId();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
