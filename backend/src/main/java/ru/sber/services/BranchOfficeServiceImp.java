package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.entities.BranchOffice;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.repositories.BranchOfficeRepository;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.util.List;

@Slf4j
@Service
public class BranchOfficeServiceImp implements BranchOfficeService {
    private final BranchOfficeRepository branchOfficeRepository;

    public BranchOfficeServiceImp(BranchOfficeRepository branchOfficeRepository) {
        this.branchOfficeRepository = branchOfficeRepository;
    }

    @Override
    public boolean closeBranchOffice() {
        log.info("Закрывает филиал");

        BranchOffice branchOffice = getBranchOffice();

        branchOffice.setStatus("CLOSE");
        branchOfficeRepository.save(branchOffice);
        return true;
    }

    @Override
    public boolean openBranchOffice() {
        log.info("Открывает филиал");

        BranchOffice branchOffice = getBranchOffice();

        branchOffice.setStatus("OPEN");
        branchOfficeRepository.save(branchOffice);
        return true;
    }

    @Override
    public BranchOffice getBranchOfficeByEmployee() {
        log.info("Получает информацию о филиале по сотруднику");

        return getBranchOffice();
    }

    @Override
    public List<BranchOffice> getListBranchOffice() {
        log.info("Получает информацию о всех филиалах");

        return branchOfficeRepository.findAll();
    }

    private BranchOffice getBranchOffice() {
        log.info("Получает id сотрудника текущей сессии");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getBranchOffice();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
