package com.bfwg.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;
import javax.naming.OperationNotSupportedException;

@Builder
@Getter
public class ExpiredUserAccount implements UserDetails {

    public void setPassword(String password) {
        throw new RuntimeException("setPassword is not supported by this object");
    }

    private String password;
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String username;
    private boolean enabled;
    private long lastPasswordRestTime;
    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new RuntimeException("getAuthorities is not supported by this object");
    }

//    @Override //this is the key method for jwt
//    public String getUsername() {
//        return email;
//    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new RuntimeException("isAccountNonLocked is not supported by this object");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new RuntimeException("isCredentialsNonExpired is not supported by this object");
    }

    public Date getLastPasswordResetDate() {
        throw new RuntimeException("getLastPasswordResetDate is not supported by this object");
    }

}
