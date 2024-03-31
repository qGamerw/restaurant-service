package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.entities.Category;
import ru.sber.entities.DishesBranchOffice;
import ru.sber.services.DishBranchOfficeService;

import java.util.List;

/**
 * Контроллер для взаимодействия {@link Category категориями}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("dishes-branch-office")
public class DishBranchOfficeController {
    private final DishBranchOfficeService dishBranchOfficeService;

    @Autowired
    public DishBranchOfficeController(DishBranchOfficeService dishBranchOfficeService) {
        this.dishBranchOfficeService = dishBranchOfficeService;
    }

    /**
     * Получает блюда по филиалу
     *
     * @return результат
     */
    @GetMapping
    public ResponseEntity<List<DishesBranchOffice>> getListDishBranchOffice() {
        log.info("Получает блюда по филиалу");

        return ResponseEntity
                .ok()
                .body(dishBranchOfficeService.getListDishBranchOffice());
    }

    /**
     * Получает все блюда
     *
     * @return результат
     */
    @GetMapping("/all")
    public ResponseEntity<List<DishesBranchOffice>> getListDishBranchOfficeAll() {
        log.info("Получает все блюда");

        return ResponseEntity
                .ok()
                .body(dishBranchOfficeService.getListDishBranchOfficeAll());
    }
}
