package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Dish;
import ru.sber.model.ListDishes;
import ru.sber.services.DishService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для взаимодействия {@link Dish блюдами}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/")
    public ResponseEntity<Long> addDish(@RequestBody Dish dish) {
        log.info("Добавляет блюдо с именем {}", dish.getName());

        return ResponseEntity.created(URI.create("dishes/" + dishService.addDish(dish)))
                .build();
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addDishByName(@RequestBody Dish dish) {
        log.info("Добавляет блюдо по имени");

        var isAdd = dishService.addDishByName(dish.getName());

        if (isAdd) {
            return ResponseEntity.accepted()
                    .build();
        } else {
            return ResponseEntity.badRequest()
                    .build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Long> updateDish(@RequestBody Dish dish) {
        log.info("Обновляет блюдо");

        var isUpdate = dishService.updateDish(dish);

        if (isUpdate) {
            return ResponseEntity.accepted()
                    .build();
        } else {
            return ResponseEntity.badRequest()
                    .build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getListDish() {
        log.info("Получает блюда в филиале");

        List<Dish> dishes = dishService.getListDish();

        return ResponseEntity.ok()
                .body(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable long id) {
        log.info("Получает блюдо по id");

        Optional<Dish> dish = dishService.getDishById(id);

        return dish.map(
                        value -> ResponseEntity.ok()
                                .body(value))
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    @GetMapping("/any")
    public ResponseEntity<Page<Dish>> getListAllDish(@RequestParam int page, @RequestParam int size) {
        log.info("Получает все блюда");

        Page<Dish> dishes = dishService.getDishesByPage(page, size);

        return ResponseEntity.ok()
                .body(dishes);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getListAllDish(@RequestParam String city) {
        log.info("Получает все блюда в городе");

        List<Dish> dishes = dishService.getListByNameCity(city);

        return ResponseEntity.ok()
                .body(dishes);
    }

    @GetMapping("/basket")
    public ResponseEntity<List<Dish>> getListDishesById(@RequestBody ListDishes listDishesId) {
        log.info("Получает все блюда по id");

        List<Dish> dishes = dishService.getListById(listDishesId);

        return ResponseEntity.ok()
                .body(dishes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Dish> deleteDishById(@PathVariable long id) {
        log.info("Удаляет из филиала блюдо по id");

        boolean isDeleted = dishService.deleteDish(id);

        if (isDeleted) {
            return ResponseEntity.noContent()
                    .build();
        } else {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
