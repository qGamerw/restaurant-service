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
    public boolean openCloseBranchOffice(long branchId) {
        log.info("Закрывает/Открывает филиал");

        BranchOffice branchOffice = getBranchOffice().gerBranchOffice();

        if (branchOffice.getId() == branchId){
            branchOffice.setStatus(branchOffice.getStatus().equals("OPEN")? "CLOSE" : "OPEN");
            branchOfficeRepository.save(branchOffice);
            return true;
        }

        return false;
    }

    @Override
    public BranchOffice getBranchOfficeByEmployee() {
        log.info("Получает информацию о филиале по сотруднику");

        return getBranchOffice().gerBranchOffice();
    }

    @Override
    public List<BranchOffice> getListBranchOffice() {
        log.info("Получает информацию о всех филиалах");

        return branchOfficeRepository.findAll();
    }

    private EmployeeDetailsImpl getBranchOffice() {
        log.info("Получает id сотрудника текущей сессии");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee);
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
