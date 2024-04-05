package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.entities.DishesBranchOffice;
import ru.sber.repositories.DishesBranchOfficeRepository;

import java.util.List;

@Service
public class DishBranchOfficeServiceImp implements DishBranchOfficeService {
    private final DishesBranchOfficeRepository dishesBranchOfficeRepository;
    private final UserService userService;

    public DishBranchOfficeServiceImp(
            DishesBranchOfficeRepository dishesBranchOfficeRepository,
            UserService userService) {
        this.dishesBranchOfficeRepository = dishesBranchOfficeRepository;
        this.userService = userService;
    }

    @Override
    public List<DishesBranchOffice> getListDishBranchOffice() {
        return dishesBranchOfficeRepository.findByBranchOffice_Id(
                userService.getBranchOffice().getId());
    }

    @Override
    public List<DishesBranchOffice> getListDishBranchOfficeAll() {
        return dishesBranchOfficeRepository.findAll();
    }
}
