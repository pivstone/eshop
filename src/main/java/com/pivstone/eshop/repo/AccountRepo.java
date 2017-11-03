package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {
    Optional<Account> findByUsername(String username);
}
