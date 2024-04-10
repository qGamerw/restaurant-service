package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.BranchOffice;
import ru.sber.models.BranchOfficeLimit;
import ru.sber.services.BranchOfficeService;

import java.util.List;

/**
 * Контроллер для взаимодействия {@link BranchOffice филиалами}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("branch-offices")
public class BranchOfficeController {
    private final BranchOfficeService branchOfficeService;

    @Autowired
    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    /**
     * Открывает или закрывает филиал
     *
     * @return Результат
     */
    @PutMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> openCloseBranchOffice() {
        log.info("Закрывает или открывает филиал");

        return branchOfficeService.openCloseBranchOffice()
                .map(s -> ResponseEntity
                        .ok()
                        .body(s))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Недостаточно прав для выполнения команды"));
    }

    /**
     * Выводит данные о филиале
     *
     * @return Результат
     */
    @GetMapping
    public ResponseEntity<BranchOfficeLimit> getBranchOfficeByEmployee() {
        log.info("Выводит данные о филиале");

        return ResponseEntity
                .ok()
                .body(branchOfficeService.getBranchOfficeByEmployee());
    }

    /**
     * Выводит данные о всех филиалах
     *
     * @return Результат
     */
    @GetMapping("/all")
    public ResponseEntity<List<BranchOfficeLimit>> getListBranchOffice() {
        log.info("Выводит данные о всех филиалах");

        return ResponseEntity
                .ok()
                .body(branchOfficeService.getListBranchOffice());
    }
}
