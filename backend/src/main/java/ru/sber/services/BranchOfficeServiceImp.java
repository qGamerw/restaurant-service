package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.model.BranchOfficeLimit;
import ru.sber.repositories.BranchOfficeRepository;

import java.util.List;

@Slf4j
@Service
public class BranchOfficeServiceImp implements BranchOfficeService {
    private final BranchOfficeRepository branchOfficeRepository;
    private final UserService userService;

    @Autowired
    public BranchOfficeServiceImp(BranchOfficeRepository branchOfficeRepository,
                                  UserService userService) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.userService = userService;
    }

    @Override
    public boolean openCloseBranchOffice() {
        log.info("Закрывает/Открывает филиал");

        if (userService.countActiveUserByBranchOffice() == 1) {
            var user = userService.getUser();

            var branchOffice = user.getBranchOffice();
            branchOffice.setStatus(branchOffice.getStatus().equals("OPEN") ? "CLOSE" : "OPEN");
            branchOfficeRepository.save(branchOffice);
            return true;
        }
        return false;
    }

    @Override
    public BranchOfficeLimit getBranchOfficeByEmployee() {
        log.info("Получает информацию о филиале по сотруднику");

        return new BranchOfficeLimit(userService.getUser().getBranchOffice());
    }

    @Override
    public List<BranchOfficeLimit> getListBranchOffice() {
        log.info("Получает информацию о всех филиалах");

        return branchOfficeRepository.findAll()
                .stream()
                .map(BranchOfficeLimit::new)
                .toList();
    }
}
