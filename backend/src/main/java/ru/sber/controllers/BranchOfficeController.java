package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Employee;
import ru.sber.services.BranchOfficeService;

import java.util.List;

/**
 * Контроллер для взаимодействия {@link Employee сотрудником}
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("branch-office")
public class BranchOfficeController {
    private final BranchOfficeService branchOfficeService;

    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    // Под вопросом, где лучше сделать (автоматически)
    @PutMapping("/close")
    public ResponseEntity<?> closeBranchOffice() {
        log.info("Закрывает филиал");

        boolean isStatus = branchOfficeService.closeBranchOffice();

        if (isStatus) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("/open")
    public ResponseEntity<Void> openBranchOffice() {
        log.info("Открывает филиал");

        boolean isStatus = branchOfficeService.openBranchOffice();

        if (isStatus) {
            return ResponseEntity
                    .ok()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<BranchOffice> getBranchOfficeByEmployee() {
        log.info("Выводит данные о филиале");

        BranchOffice branchOffice = branchOfficeService.getBranchOfficeByEmployee();
        return ResponseEntity
                .ok()
                .body(branchOffice);
    }

    @GetMapping("/all-info")
    public ResponseEntity<List<BranchOffice>> getListBranchOffice() {
        log.info("Выводит данные о всех филиалах");

        List<BranchOffice> branchOffice = branchOfficeService.getListBranchOffice();
        return ResponseEntity
                .ok()
                .body(branchOffice);
    }
}
