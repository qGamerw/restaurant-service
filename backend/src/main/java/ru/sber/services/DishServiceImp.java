package ru.sber.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.sber.entities.Dish;
import ru.sber.entities.DishesBranchOffice;
import ru.sber.repositories.DishRepository;
import ru.sber.repositories.DishesBranchOfficeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DishServiceImp implements DishService {
    private final DishRepository dishRepository;
    private final DishesBranchOfficeRepository dishesBranchOfficeRepository;
    private final UserService userService;

    @Autowired
    public DishServiceImp(DishRepository dishRepository,
                          DishesBranchOfficeRepository dishesBranchOfficeRepository,
                          UserService userService) {
        this.dishRepository = dishRepository;
        this.dishesBranchOfficeRepository = dishesBranchOfficeRepository;
        this.userService = userService;
    }

    //TODO: переделать добавление блюда

    @Override
    @Transactional
    public long addDish(Dish dish) {
        var isExistsDish = dishRepository.existsByName(dish.getName());
        if (!isExistsDish) {
            dishRepository.save(dish);
        }
        return dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, userService.getBranchOffice()))
                .getDish()
                .getId();
    }

    @Override
    @Transactional
    public Optional<String> addDishByName(String name) {
        var isExistsDish = dishRepository.existsByName(name);
        Dish dish = dishRepository.findByName(name);
        var idBranchOffice = userService.getBranchOffice();

        if (isExistsDish && !dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(
                idBranchOffice.getId(), dish.getId())) {

            dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, idBranchOffice));
            return Optional.of("Блюдо успешно добавлено.");
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<String> updateDish(Dish dish) {
        var isExists = dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(
                userService.getBranchOffice().getId(),
                dish.getId());

        if (isExists) {
            dishRepository.save(dish);
            return Optional.of("Блюдо успешно обновлено.");
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<String> deleteDish(long id) {
        var isExistsDish = dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(
                userService.getBranchOffice().getId(),
                id);
        if (isExistsDish) {
            dishesBranchOfficeRepository.deleteByDish_Id(id);
            return Optional.of("Блюдо успешно удалено.");
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<Dish> getListDish() {
        return dishesBranchOfficeRepository
                .findByBranchOffice_Id(userService.getBranchOffice().getId())
                .stream()
                .map(DishesBranchOffice::getDish)
                .toList();
    }

    @Override
    public List<Dish> getListByNameCity(String name) {
        return dishesBranchOfficeRepository.findByBranchOffice_NameCity(name)
                .stream()
                .map(DishesBranchOffice::getDish)
                .toList();
    }

    @Override
    public List<Dish> getListById(String listDishes) {
        List<Long> dishIds = Arrays.stream(listDishes.split(","))
                .map(Long::parseLong)
                .toList();

        return dishRepository.findAllById(dishIds).stream()
                .toList();
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        var isExists = dishesBranchOfficeRepository.existsByDish_Id(id);

        if (isExists) {
            return dishRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<Dish> getDishesByPage(int page, int size) {
        return dishRepository.findAll(PageRequest.of(page, size));
    }
}
