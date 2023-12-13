package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.User;
import ru.sber.entities.enums.ERole;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.repositories.BranchOfficeRepository;
import ru.sber.repositories.RoleRepository;
import ru.sber.repositories.UserRepository;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchOfficeRepository branchOfficeRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository,
                          RoleRepository roleRepository,
                          BranchOfficeRepository branchOfficeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchOfficeRepository = branchOfficeRepository;
    }

    @Override
    public boolean addUserById(String userId, String idBranchOffice) {
        log.info("Регистрация сотрудника");

        var user = new User(userId);
        var userRole = roleRepository.findByRole(ERole.SELLER)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        var branchOffice = branchOfficeRepository.findById(Long.parseLong(idBranchOffice))
                .orElseThrow(() -> new RuntimeException("Филиал не найден"));

        user.setRole(userRole);
        user.setBranchOffice(branchOffice);
        user.setStatus(EStatusEmployee.UNDER_CONSIDERATION);
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        log.info("Удаляет сотрудника по id");

        return userRepository.deleteById(id);
    }
}
