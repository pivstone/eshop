package com.pivstone.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pivstone.eshop.auth.AdminAuthority;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Data
@Entity
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    // password should be encrypted, but it just a demo
    @JsonIgnore
    private String password;

    private Boolean admin;
    private Boolean enabled = Boolean.TRUE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> set = new HashSet<>();
        set.add(new AdminAuthority());
        return set;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
