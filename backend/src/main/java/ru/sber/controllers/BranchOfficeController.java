package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.BranchOffice;
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

    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    @PutMapping
    public ResponseEntity<?> openCloseBranchOffice(@RequestBody BranchOffice branchOffice) {
        log.info("Закрывает и открывает филиал");

        boolean isStatus = branchOfficeService.openCloseBranchOffice(branchOffice.getId());

        if (isStatus) {
            return ResponseEntity.ok()
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied");
        }
    }

    @GetMapping
    public ResponseEntity<BranchOffice> getBranchOfficeByEmployee() {
        log.info("Выводит данные о филиале");

        BranchOffice branchOffice = branchOfficeService.getBranchOfficeByEmployee();
        return ResponseEntity.ok()
                .body(branchOffice);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BranchOffice>> getListBranchOffice() {
        log.info("Выводит данные о всех филиалах");

        List<BranchOffice> branchOffice = branchOfficeService.getListBranchOffice();
        return ResponseEntity.ok()
                .body(branchOffice);
    }
}
