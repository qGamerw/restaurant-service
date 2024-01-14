package ru.sber.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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

    @Override
    @Transactional
    public long addDish(Dish dish) {
        log.info("Добавляет блюдо с именем {}", dish.getName());

        var isExistsDish = dishRepository.existsByName(dish.getName());
        if (!isExistsDish) {
            dishRepository.save(dish);
        }
        return dishesBranchOfficeRepository.save(
                new DishesBranchOffice(dish, userService.getBranchOffice())).getDish().getId();
    }

    @Override
    @Transactional
    public boolean addDishByName(String name) {
        log.info("Добавляет блюдо с менем {}", name);

        var isExistsDish = dishRepository.existsByName(name);
        Dish dish = dishRepository.findByName(name);
        var idBranchOffice = userService.getBranchOffice();

        if (isExistsDish && !dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(
                idBranchOffice.getId(), dish.getId())) {

            dishesBranchOfficeRepository.save(new DishesBranchOffice(dish, idBranchOffice));
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateDish(Dish dish) {
        log.info("Обновляет блюдо с именем {}", dish.getName());

        var isExists = dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(
                userService.getBranchOffice().getId(), dish.getId());

        if (isExists) {
            dishRepository.save(dish);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteDish(long id) {
        var idBranchOffice = userService.getBranchOffice().getId();
        log.info("Удаляет из филиала блюдо с id {} {}", id, idBranchOffice);

        var isExistsDish = dishesBranchOfficeRepository.existsByBranchOffice_IdAndDish_Id(idBranchOffice, id);
        if (isExistsDish) {
            dishesBranchOfficeRepository.deleteByDish_Id(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public List<Dish> getListDish() {
        log.info("Получает все блюда в филиале");

        return dishesBranchOfficeRepository
                .findByBranchOffice_Id(userService.getBranchOffice().getId())
                .stream()
                .map(DishesBranchOffice::getDish)
                .toList();
    }

    @Override
    public List<Dish> getListByNameCity(String name) {
        log.info("Получает блюдо с в городе {}", name);

        return dishesBranchOfficeRepository.findByBranchOffice_NameCity(name).stream()
                .map(DishesBranchOffice::getDish)
                .toList();
    }

    @Override
    public List<Dish> getListById(String listDishes) {
        log.info("Получает блюдо с ListId {}", listDishes);

        List<Long> dishIds = Arrays.stream(listDishes.split(","))
                .map(Long::parseLong)
                .toList();

        return dishRepository.findAllById(dishIds).stream()
                .toList();
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        log.info("Получает блюдо с id {}", id);

        var isExists = dishesBranchOfficeRepository.existsByDish_Id(id);

        if (isExists) {
            return dishRepository.findById(id);
        }

        return Optional.empty();
    }

    @Override
    public Page<Dish> getDishesByPage(int page, int size) {
        log.info("Получает блюдо по страницам");

        return dishRepository.findAll(PageRequest.of(page, size));
    }
}
