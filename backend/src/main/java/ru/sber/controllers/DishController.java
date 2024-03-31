package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Dish;
import ru.sber.services.DishService;

import java.util.List;

/**
 * Контроллер для взаимодействия {@link Dish блюдами}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("dishes")
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * Добавляет блюдо в офис
     *
     * @return результат
     */
    @PostMapping("/")
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> addDish(@RequestBody Dish dish) {
        log.info("Добавляет блюдо с именем {}", dish.getName());

        return new ResponseEntity<>(
                "Успешно добавлено блюдо с id: " + dishService.addDish(dish),
                HttpStatus.CREATED);
    }

    /**
     * Добавляет блюдо по имени в офис
     *
     * @return результат
     */
    @PostMapping("/add")
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> addDishByName(@RequestBody Dish dish) {
        log.info("Добавляет блюдо по имени");

        var isAdd = dishService.addDishByName(dish.getName());

        return isAdd
                .map(s -> new ResponseEntity<>(s, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>("Не удалось найти блюдо.", HttpStatus.NOT_FOUND));
    }

    /**
     * Обновляет блюдо в офисе
     *
     * @return результат
     */
    @PutMapping
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> updateDish(@RequestBody Dish dish) {
        log.info("Обновляет блюдо по id {}", dish.getId());

        var isUpdate = dishService.updateDish(dish);

        return isUpdate
                .map(s -> new ResponseEntity<>(s, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>("Не удалось найти блюдо.", HttpStatus.NOT_FOUND));
    }

    /**
     * Выводит все блюда в офисе
     *
     * @return результат
     */
    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getListDish() {
        log.info("Получает блюда в филиале");

        return ResponseEntity
                .ok()
                .body(dishService.getListDish());
    }

    /**
     * Получает блюдо по id для клиента
     *
     * @param id id блюда
     * @return результат
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable long id) {
        log.info("Получает блюдо по id");

        return dishService.getDishById(id)
                .map(value -> ResponseEntity
                        .ok()
                        .body(value))
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    /**
     * Получает все блюда для клиента
     *
     * @param page номер страницы
     * @param size размер страницы
     * @return результат
     */
    @GetMapping("/customer/any")
    public ResponseEntity<Page<Dish>> getListAllDish(@RequestParam int page, @RequestParam int size) {
        log.info("Получает все блюда");

        return ResponseEntity
                .ok()
                .body(dishService.getDishesByPage(page, size));
    }

    /**
     * Получает все блюда в городе для клиента
     *
     * @param city город
     * @return результат
     */
    @GetMapping("/customer")
    public ResponseEntity<List<Dish>> getListAllDish(@RequestParam String city) {
        log.info("Получает все блюда в городе");

        return ResponseEntity
                .ok()
                .body(dishService.getListByNameCity(city));
    }

    /**
     * Получает список блюд с id для клиента
     *
     * @param list список id
     * @return результат
     */
    @GetMapping("/customer/basket")
    public ResponseEntity<List<Dish>> getListDishesById(@RequestParam String list) {
        log.info("Получает список блюд по id {}", list);

        return ResponseEntity
                .ok()
                .body(dishService.getListById(list));
    }

    /**
     * Удаляет блюда в офисе
     *
     * @param id id блюда
     * @return результат
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> deleteDishById(@PathVariable long id) {
        log.info("Удаляет из филиала блюдо по id");

        var isDeleted = dishService.deleteDish(id);

        return isDeleted
                .map(s -> new ResponseEntity<>(s, HttpStatus.NO_CONTENT))
                .orElseGet(() -> new ResponseEntity<>("Не удалось найти блюдо.", HttpStatus.NOT_FOUND));
    }
}