package com.nickrossdev.authentication.domain.authentication.repositories;

import com.nickrossdev.authentication.domain.authentication.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);
}
