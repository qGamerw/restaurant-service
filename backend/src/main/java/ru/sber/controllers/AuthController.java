package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Employee;
import ru.sber.entities.Position;
import ru.sber.entities.enums.EPosition;
import ru.sber.entities.request.LoginRequest;
import ru.sber.entities.request.SignupRequest;
import ru.sber.entities.response.JwtResponse;
import ru.sber.entities.response.MessageResponse;
import ru.sber.security.jwt.JwtUtils;
import ru.sber.security.services.EmployeeDetailsImpl;
import ru.sber.services.EmployeeService;
import ru.sber.services.PositionService;

import java.util.Optional;

/**
 * Контроллер для взаимодействия с регистрацией и входом у {@link Employee сотрудника}
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          EmployeeService employeeService,
                          PositionService positionService,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.positionService = positionService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("Регистрация сотрудника с email {}", signUpRequest.getEmail());

        if (employeeService.existsByEmployeeName(signUpRequest.getEmployeeName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Login уже существует"));
        }

        if (employeeService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email уже используется"));
        }

        Employee employee = new Employee(
                signUpRequest.getEmployeeName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Position employeePosition = positionService
                .findByName(EPosition.POSITION_SELLER)
                .orElseThrow(() -> new RuntimeException("Должность не найдена"));

        employee.setPosition(employeePosition);
        employee.setBranchOffice(new BranchOffice(Long.parseLong(signUpRequest.getBranchOffice())));

        employeeService.addEmployee(employee);

        return getResponseEntity(signUpRequest.getEmployeeName(), signUpRequest.getPassword());
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateEmployee(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Вход сотрудника с логином {}", loginRequest.getEmployeeName());

        return getResponseEntity(loginRequest.getEmployeeName(), loginRequest.getPassword());
    }

    private ResponseEntity<?> getResponseEntity(String employeeName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(employeeName, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();

        Optional<? extends GrantedAuthority> optional = employeeDetails.getAuthorities()
                .stream()
                .findFirst();

        Position position = optional
                .map(grantedAuthority -> new Position(EPosition.valueOf(grantedAuthority.getAuthority())))
                .orElseGet(() -> new Position(EPosition.POSITION_SELLER));

        return ResponseEntity.ok(new JwtResponse(
                jwtUtils.generateJwtToken(authentication),
                employeeDetails.getId(),
                employeeDetails.getUsername(),
                employeeDetails.getEmail(),
                employeeDetails.getBranchOffice(),
                position));
    }

}
