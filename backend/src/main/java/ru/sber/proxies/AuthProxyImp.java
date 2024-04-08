package ru.sber.proxies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.OrderToken;
import ru.sber.exceptions.GenerateTokenException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.models.*;
import ru.sber.observers.EmailObserver;
import ru.sber.repositories.OrderTokenRepository;
import ru.sber.services.UserService;
import ru.sber.singleton.GenerateTokenPasswordSequenceSingleton;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Primary
@Service
public class AuthProxyImp implements AuthProxy {
    private final OrderTokenRepository orderTokenRepository;
    private final UserService userService;
    private final EmailObserver emailObserver;
    private final GenerateTokenPasswordSequenceSingleton generateToken;

    private final AuthProxy AuthResources;

    @Autowired
    public AuthProxyImp(OrderTokenRepository orderTokenRepository,
                        UserService userService,
                        EmailObserver emailObserver,
                        @Qualifier("keyCloak") AuthProxy AuthResources) {
        this.orderTokenRepository = orderTokenRepository;
        this.userService = userService;
        this.emailObserver = emailObserver;
        this.generateToken = GenerateTokenPasswordSequenceSingleton.getInstance();
        this.AuthResources = AuthResources;
    }

    @Override
    public OrderToken updateOrderToken() {
        var token = AuthResources.updateOrderToken();

        orderTokenRepository.save(token);
        return token;
    }

    @Override
    public ResponseEntity<String> signUpUserREST(SignupRequest signupRequest) {
        try {
            ResponseEntity<String> userResponseEntity = AuthResources.signUpUserREST(signupRequest);
            log.info("Статус запроса регистрацию пользователя: {}", userResponseEntity.getStatusCode());

            String responseHeader = userResponseEntity.getHeaders().get("Location").get(0);

            int lastSlashIndex = responseHeader.lastIndexOf("/");
            String userId = responseHeader.substring(lastSlashIndex + 1);

            userService.addUserById(userId, signupRequest.getIdBranchOffice());
            return new ResponseEntity<>("Регистрация прошла успешно.", userResponseEntity.getStatusCode());
        } catch (NullPointerException e) {
            log.error("Ошибка регистрации: Не удалось получить данные из заголовка.");
            return new ResponseEntity<>("Ошибка регистрации: Не удалось получить данные из заголовка.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> signInUserREST(LoginRequest loginRequest) {
        ResponseEntity<String> tokenResponseEntity = AuthResources.signInUserREST(loginRequest);
        log.info("Статус запроса получение токена пользователя при входе: {}", tokenResponseEntity.getStatusCode());

        return new ResponseEntity<>(tokenResponseEntity.getBody(), tokenResponseEntity.getStatusCode());
    }

    @Override
    public ResponseEntity<String> refreshTokenREST(RefreshToken refreshToken) {
        ResponseEntity<String> tokenResponseEntity = AuthResources.refreshTokenREST(refreshToken);
        log.info("Статус запроса на обновление токена пользователя: {}", tokenResponseEntity.getStatusCode());

        return new ResponseEntity<>(tokenResponseEntity.getBody(), tokenResponseEntity.getStatusCode());
    }

    @Override
    public ResponseEntity<String> updateUserInfoREST(
            SignupRequest signupRequest,
            UpdateUserData updateUserData,
            String idUser) {

        if (signupRequest.getEmail() != null) {
            updateUserData.setEmail(signupRequest.getEmail());
        }

        if (signupRequest.getFirstName() != null) {
            updateUserData.setFirstName(signupRequest.getFirstName());
        }

        if (signupRequest.getLastName() != null) {
            updateUserData.setLastName(signupRequest.getLastName());
        }

        if (signupRequest.getPhoneNumber() != null) {
            Attributes attributes = new Attributes();
            attributes.setPhoneNumber(signupRequest.getPhoneNumber());

            updateUserData.setAttributes(attributes);
        }

        ResponseEntity<String> userResponseEntity = AuthResources.updateUserInfoREST(signupRequest, updateUserData, idUser);
        log.info("Статус запроса на обновления данных: {}", userResponseEntity.getStatusCode());

        var user = userService.findByContext();
        var idBranchOffice = signupRequest.getIdBranchOffice();
        user.setBranchOffice(
                (idBranchOffice == null) ? user.getBranchOffice() : new BranchOffice(Long.parseLong(idBranchOffice))
        );

        userService.userUpdate(user);
        return new ResponseEntity<>(
                "Успешное обновление информации пользователя.",
                userResponseEntity.getStatusCode());
    }

    @Override
    public ResponseEntity<String> sendPasswordToken(ResetPassword resetPassword) {
        try {
            ResponseEntity<String> userResponseEntity = AuthResources.sendPasswordToken(resetPassword);
            log.info("Статус запроса получение токена пользователя при проверке email: {}", userResponseEntity.getStatusCode());

            JsonNode usersNode = new ObjectMapper().readTree(userResponseEntity.getBody());

            if (!usersNode.isArray() || usersNode.size() <= 0) {
                throw new UserNotFoundException("Ошибка отправки токена: Пользователь не найден.");
            }

            JsonNode userNode = usersNode.get(0);
            var user = userService.findById(userNode.get("id").asText());

            String resetTokenPassword = generateToken.generateToken();
            user.setResetTokenPassword(resetTokenPassword);
            userService.userUpdate(user);
            resetPassword.setToken(resetTokenPassword);

            emailObserver.sendResetPasswordToken(resetPassword);
            return new ResponseEntity<>("Успешно отправили токен по почте.", userResponseEntity.getStatusCode());
        } catch (UserNotFoundException e) {
            log.error("Ошибка отправки токена: Пользователь не найден.");
            return new ResponseEntity<>("Ошибка отправки токена: Пользователь не найден.", HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            log.error("Ошибка отправки токена: Не удалось отправить письмо по почте.");
            return new ResponseEntity<>("Ошибка отправки токена: Не удалось отправить письмо по почте.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сброса пароля: Не удалось получить токен администратора.");
            return new ResponseEntity<>("Ошибка сброса пароля: Не удалось получить токен администратора.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchAlgorithmException e) {
            log.error("Ошибка сброса пароля: Не удалось сгенерировать токен.");
            throw new GenerateTokenException("Ошибка сброса пароля: Не удалось сгенерировать токен.");
        }
    }

    @Override
    public ResponseEntity<String> updateUserPassword(ResetPassword resetPassword) {
        try {
            ResponseEntity<String> resetResponseEntity = AuthResources.updateUserPassword(resetPassword);
            log.info("Статус запроса сброса пароля: {}", resetResponseEntity.getStatusCode());

            emailObserver.sendUpdatePasswordToken(resetPassword);
            return new ResponseEntity<>("Успешное обновление пароля.", resetResponseEntity.getStatusCode());
        } catch (MessagingException e) {
            log.error("Ошибка сброса пароля: Не удалось отправить письмо по почте.");
            return new ResponseEntity<>("Ошибка сброса пароля: Не удалось отправить письмо по почте.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String idUser, Jwt jwt) {
        if (userService.deleteById(idUser)) {
            ResponseEntity<String> responseEntity = AuthResources.deleteUser(idUser, jwt);
            return new ResponseEntity<>("Пользователь успешно удален.", responseEntity.getStatusCode());
        } else {
            return new ResponseEntity<>(
                    "Ошибка удаления пользователя: Пользователь не найден.",
                    HttpStatus.NOT_FOUND);
        }
    }
}
