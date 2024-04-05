package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.exceptions.BranchOfficeNotFoundException;
import ru.sber.exceptions.TokenNotFoundException;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.repositories.BranchOfficeRepository;
import ru.sber.repositories.UserRepository;
import ru.sber.security.JwtService;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BranchOfficeRepository branchOfficeRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BranchOfficeRepository branchOfficeRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.branchOfficeRepository = branchOfficeRepository;
        this.jwtService = jwtService;
    }

    /**
     * Получение токена из контекста
     *
     * @return Токен из контекста
     */
    private static Jwt getUserJwtTokenSecurityContext() throws TokenNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            return jwtAuthenticationToken.getToken();
        } else {
            throw new TokenNotFoundException("Ошибка чтения токена: Токен пользователя не найден.");
        }
    }

    @Override
    public void addUserById(String userId, String idBranchOffice) throws BranchOfficeNotFoundException {
        userRepository.save(new User(
                userId,
                branchOfficeRepository
                        .findById(Long.parseLong(idBranchOffice))
                        .orElseThrow(() -> new BranchOfficeNotFoundException("Ошибка поиска филиала: Филиал не найден.")),
                EStatusEmployee.INACTIVE));

    }

    @Override
    @Transactional
    public boolean deleteById(String userId) {
        return userRepository.deleteById(userId) > 0;
    }

    @Override
    public User findById(String userId) throws UserNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Ошибка поиска пользователя: Пользователь не найден"));
    }

    @Override
    public User findByContext() throws UserNotFoundException {
        return userRepository
                .findById(jwtService.getSubClaim(getUserJwtTokenSecurityContext()))
                .orElseThrow(() -> new UserNotFoundException("Ошибка поиска пользователя: Пользователь не найден"));
    }

    @Override
    public void logOutUser() {
        var user = findByContext();

        if (!user.getStatus().name().equals(EStatusEmployee.UNDER_CONSIDERATION.name())) {
            user.setStatus(EStatusEmployee.INACTIVE);
            userUpdate(user);
        }
    }

    @Override
    public void userUpdate(User user) {
        userRepository.save(user);
    }

    @Override
    public String getUserToken(String userId) {
        return findById(userId).getResetTokenPassword();
    }

    @Override
    public void deleteTokenById(String userId) {
        var user = findById(userId);
        user.setResetTokenPassword("");

        userRepository.save(user);
    }

    @Override
    public User getUser() throws UserNotFoundException {
        return userRepository
                .findById(jwtService.getSubClaim(getUserJwtTokenSecurityContext()))
                .orElseThrow(() -> new UserNotFoundException("Ошибка поиска пользователя: Пользователь не найден"));
    }

    @Override
    public BranchOffice getBranchOffice() {
        return getUser().getBranchOffice();
    }

    @Override
    public int countActiveUserByBranchOffice() {
        return userRepository.countByBranchOffice_IdAndStatus(
                getUser().getBranchOffice().getId(),
                EStatusEmployee.INACTIVE);
    }
}
