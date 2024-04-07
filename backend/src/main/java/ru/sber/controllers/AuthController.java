package ru.sber.controllers;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.TokenNotFoundException;
import ru.sber.models.LoginRequest;
import ru.sber.models.RefreshToken;
import ru.sber.models.ResetPassword;
import ru.sber.models.SignupRequest;
import ru.sber.proxies.AuthProxy;
import ru.sber.security.JwtService;
import ru.sber.services.UserService;

/**
 * Контроллер для взаимодействия с регистрацией и входом у {@link User сотрудника}
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthProxy authProxy;

    @Autowired
    public AuthController(UserService userService,
                          JwtService jwtService,
                          AuthProxy authProxy) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authProxy = authProxy;
    }

    /**
     * Получение токена из контекста
     *
     * @return Токен из контекста
     */
    private static Jwt getJwtTokenSecurityContext() throws TokenNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getToken();
        } else {
            throw new TokenNotFoundException("Ошибка чтения токена: Токен пользователя не найден.");
        }
    }

    /**
     * Регистрация пользователя
     *
     * @param signupRequest данные для регистрации
     * @return Результат
     */
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignupRequest signupRequest) {
        log.info("Регистрация пользователя с email {}", signupRequest.getEmail());

        return authProxy.signUpUserREST(signupRequest);
    }

    /**
     * Вход пользователя
     *
     * @param loginRequest данные для входа
     * @return Результат
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody LoginRequest loginRequest) {
        log.info("Вход пользователя с Username: {}", loginRequest.getUsername());

        return authProxy.signInUserREST(loginRequest);
    }

    /**
     * Обновление токена пользователя
     *
     * @param refreshToken токен пользователя
     * @return Результат
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshToken refreshToken) {
        log.info("Обновление токена у пользователя");

        return authProxy.refreshTokenREST(refreshToken);
    }

    /**
     * Получение данных пользователя
     *
     * @return Результат
     */
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        try {
            log.info("Получение информации об пользователе.");

            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setContentType(MediaType.APPLICATION_JSON);
            Jwt jwt = getJwtTokenSecurityContext();

            var user = userService.findByContext();
            if (!user.getStatus().equals(EStatusEmployee.UNDER_CONSIDERATION)) {
                user.setStatus(EStatusEmployee.ACTIVE);
                userService.userUpdate(user);
            }

            var userDetails = jwtService.getDataUser(
                    jwt,
                    user.getBranchOffice(),
                    user.getStatus().name());

            return new ResponseEntity<>(userDetails, userHeaders, HttpStatus.OK);
        } catch (TokenNotFoundException e) {
            log.error("Ошибка чтения токена: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка чтения токена: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновление данных пользователя
     *
     * @param signupRequest данные для входа на сервис KeyCloak
     * @return Результат
     */
    @PutMapping
    @Transactional
    public ResponseEntity<String> updateUserInfo(@RequestBody SignupRequest signupRequest) {
        log.info("Обновляет данные о клиенте c email {}", signupRequest.getEmail());

        try {
            Jwt jwt = getJwtTokenSecurityContext();
            return authProxy.updateUserInfoREST(
                    signupRequest,
                    jwtService.getDataUserByContext(jwt),
                    jwtService.getSubClaim(jwt));
        } catch (TokenNotFoundException e) {
            log.error("Ошибка чтения токена: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка чтения токена: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Выход пользователя из системы
     *
     * @return Результат
     */
    @PutMapping("/logout")
    public ResponseEntity<String> logOutUser() {
        log.info("Выход пользователя из системы");

        userService.logOutUser();
        return ResponseEntity
                .ok()
                .build();
    }

    /**
     * Токен для сброса пароля
     *
     * @param resetPassword данные для сброса пароля
     * @return Результат
     */
    @PostMapping("/reset-password/token")
    public ResponseEntity<String> sendPasswordToken(@RequestBody ResetPassword resetPassword) {
        log.info("Получение токена для изменения пароля у пользователя {}", resetPassword.getEmail());

        return authProxy.sendPasswordToken(resetPassword);
    }

    /**
     * Сброс пароля
     *
     * @param resetPassword данные для сброса пароля
     * @return Результат
     */
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) {
        log.info("Обновление пароля у пользователя {}", resetPassword.getEmail());

        return authProxy.updateUserPassword(resetPassword);
    }

    /**
     * Удаление пользователя
     *
     * @return Результат
     */
    @DeleteMapping
    public ResponseEntity<String> deleteEmployeeById() {
        log.info("Удаление аккаунта сотрудника");

        try {
            Jwt jwt = getJwtTokenSecurityContext();
            return authProxy.deleteUser(jwtService.getSubClaim(jwt), jwt);
        } catch (TokenNotFoundException e) {
            log.error("Ошибка чтения токена: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка чтения токена: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        }
    }
}