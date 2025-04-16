package com.nickrossdev.authentication.domain.authentication.services;

import com.nickrossdev.authentication.domain.authentication.models.AccountRole;
import com.nickrossdev.authentication.domain.authentication.repositories.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;

    public AccountRole registerNewAccountRole (AccountRole accountRole) {
        return accountRoleRepository.save(accountRole);
    }
}
