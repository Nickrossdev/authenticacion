package com.nickrossdev.authentication.domain.authentication.repositories;

import com.nickrossdev.authentication.domain.authentication.models.Account;
import com.nickrossdev.authentication.domain.authentication.models.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
    List<AccountRole> findByAccount(Account account);
}
