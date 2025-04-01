package com.security.DTO;

import java.util.Collection;

import com.security.entity.User;
import org.springframework.security.core.GrantedAuthority;


import lombok.Getter;

@Getter
public class AuthUserDTO extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String fullName;

    public AuthUserDTO(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);

        this.userId = user.getId();
        this.fullName = user.getName();
    }

}