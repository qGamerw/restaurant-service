package ru.sber.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.entities.Role;
import ru.sber.entities.enums.ERole;
import ru.sber.repositories.RoleRepository;

import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByRole(ERole eRole) {
        log.info("Ищет должность {}", eRole);

        return roleRepository.findByRole(eRole);
    }
}
