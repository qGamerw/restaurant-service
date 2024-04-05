package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.models.BranchOfficeLimit;
import ru.sber.repositories.BranchOfficeRepository;

import java.util.List;
import java.util.Optional;

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
    public Optional<String> openCloseBranchOffice() {
        if (userService.countActiveUserByBranchOffice() == 1) {
            var user = userService.getUser();

            var branchOffice = user.getBranchOffice();
            branchOffice.setStatus(branchOffice.getStatus().equals("OPEN") ? "CLOSE" : "OPEN");
            branchOfficeRepository.save(branchOffice);
            return Optional.of("Филиал " + branchOffice.getId() + ": " + branchOffice.getStatus());
        }
        return Optional.empty();
    }

    @Override
    public BranchOfficeLimit getBranchOfficeByEmployee() {
        return new BranchOfficeLimit(userService.getUser().getBranchOffice());
    }

    @Override
    public List<BranchOfficeLimit> getListBranchOffice() {
        return branchOfficeRepository
                .findAll()
                .stream()
                .map(BranchOfficeLimit::new)
                .toList();
    }
}
