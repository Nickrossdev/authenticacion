package com.nickrossdev.authentication.domain.authentication.services;

import com.nickrossdev.authentication.domain.authentication.dto.RegisterAccountRequest;
import com.nickrossdev.authentication.domain.authentication.mapper.AccountMapper;
import com.nickrossdev.authentication.domain.authentication.models.Account;
import com.nickrossdev.authentication.domain.authentication.repositories.AccountRepository;
import com.nickrossdev.authentication.exceptions.AccountNotFoundException;
import com.nickrossdev.authentication.exceptions.AccountPasswordIncorrectException;
import com.nickrossdev.authentication.exceptions.DuplicateAccountFieldsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public Account createNewAccount(RegisterAccountRequest request){
        Account account = accountMapper.toEntity(request);
        validateAccountUniqueness(account);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(email));
    }

    public Boolean existsUsername(String username){
        return accountRepository.existsByUsername(username);
    }
    public Boolean existsEmail(String email){
        return accountRepository.existsByEmail(email);
    }

    public void saveToken (Account account, String token) {
        account.setToken(token);
        accountRepository.save(account);
    }

    public void deleteToken (Account account) {
        account.setToken(null);
        accountRepository.save(account);
    }

    public Boolean verifyPassword(String password, String encodedPassword) {
        if(!passwordEncoder.matches(password,encodedPassword)) {
            throw new AccountPasswordIncorrectException();
        }
        return true;
    }

    private void validateAccountUniqueness(Account account) {
        Map<String,String> errors = new HashMap<>();
        if (existsUsername(account.getUsername())) {
            errors.put("username", "El nombre de usuario ya está en uso.");
        }
        if (existsEmail(account.getEmail())) {
            errors.put("email", "El correo electrónico ya está en uso.");
        }
        if (!errors.isEmpty()) {
            throw new DuplicateAccountFieldsException(errors);
        }
    }
}
