package com.nickrossdev.authentication.domain.authentication.services;

import com.nickrossdev.authentication.domain.authentication.models.Role;
import com.nickrossdev.authentication.domain.authentication.repositories.RoleRepository;
import com.nickrossdev.authentication.exceptions.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
