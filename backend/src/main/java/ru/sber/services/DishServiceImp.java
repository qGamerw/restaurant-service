package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Dish;
import ru.sber.entities.DishesBranchOffice;
import ru.sber.exceptions.NoFoundEmployeeException;
import ru.sber.repositories.DishRepository;
import ru.sber.repositories.DishesBranchOfficeRepository;
import ru.sber.security.services.EmployeeDetailsImpl;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DishServiceImp implements DishService {
    private final DishRepository dishRepository;
    private final DishesBranchOfficeRepository dishesBranchOfficeRepository;

    public DishServiceImp(DishRepository dishRepository, DishesBranchOfficeRepository dishesBranchOfficeRepository) {
        this.dishRepository = dishRepository;
        this.dishesBranchOfficeRepository = dishesBranchOfficeRepository;
    }

    @Override
    public List<Dish> getListDish() {
        return dishesBranchOfficeRepository.findByBranchOffice_Id(getBranchOffice().getId())
                .stream()
                .map(DishesBranchOffice::getDish)
                .toList();
    }

    @Override
    public List<Dish> getListAllDish() {
        return dishRepository.findAll();
    }

    @Override
    @Transactional
    public long addDish(Dish dish) {
        log.info("Добавляет блюдо с id {}", dish.getId());

        if (!dishRepository.existsByName(dish.getName())){
            var id = dishRepository.save(dish).getId();
            dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, getBranchOffice()));

            return id;
        } else {
            return dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, getBranchOffice())).getDish().getId();
        }
    }

    @Override
    @Transactional
    public boolean addDishByName(String name) {
        log.info("Добавляет блюдо с менем {}", name);

        if (dishRepository.existsByName(name)){
            Dish dish = dishRepository.findByName(name);

            dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, getBranchOffice()));
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteDish(long id) {
        log.info("Удаляет из филиала блюдо с id {} {}", id, getBranchOffice().getId());

        if (dishesBranchOfficeRepository.existsByDish_IdAndAndBranchOffice_Id(id, getBranchOffice().getId())) {
            dishesBranchOfficeRepository.deleteByDish_Id(id);

            return true;
        }

        return false;
    }

    @Override
    public boolean updateDish(Dish dish) {
        log.info("Обновляет блюдо с id {}", dish.getId());

        if (dishesBranchOfficeRepository.existsByDish_IdAndAndBranchOffice_Id(dish.getId(), getBranchOffice().getId())) {
            dishRepository.save(dish);
            return true;
        }

        return false;
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        log.info("Получает блюдо с id {}", id);

        if (dishesBranchOfficeRepository.existsByDish_IdAndAndBranchOffice_Id(id, getBranchOffice().getId())) {
            return dishRepository.findById(id);
        }

        return Optional.empty();
    }

    private BranchOffice getBranchOffice() {
        log.info("Получает id филиала");

        var employee = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (employee instanceof EmployeeDetailsImpl) {
            return ((EmployeeDetailsImpl) employee).getBranchOffice();
        } else {
            throw new NoFoundEmployeeException("Сотрудник не найден");
        }
    }
}
