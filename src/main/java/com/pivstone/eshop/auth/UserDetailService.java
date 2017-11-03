package com.pivstone.eshop.auth;


import com.pivstone.eshop.domain.Account;
import com.pivstone.eshop.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Repository
public class UserDetailService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername(name);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new UsernameNotFoundException("could not find the user '"
                    + name + "'");
        }

    }
}
