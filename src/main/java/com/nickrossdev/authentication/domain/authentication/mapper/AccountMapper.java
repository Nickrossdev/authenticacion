package com.nickrossdev.authentication.domain.authentication.mapper;

import com.nickrossdev.authentication.domain.authentication.dto.RegisterAccountRequest;
import com.nickrossdev.authentication.domain.authentication.models.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(RegisterAccountRequest request) {
        Account account = new Account();
        account.setUsername(request.username());
        account.setEmail(request.email());
        account.setPassword(request.password());
        account.setActive(true);
        return account;
    }
}
