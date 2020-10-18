package com.food.auth.service.model;

import com.food.auth.domain.model.Usuario;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AuthUser extends User {

    private static final long serialVersionUID = 531L;
    private String fullName;
    private Long userId;

    public AuthUser(Usuario usuario) {
        super(usuario.email(), usuario.senha(), Collections.emptyList());
        this.fullName = usuario.nome();
        this.userId = usuario.id();
    }

    public String getFullName() {
        return fullName;
    }

    public Long getUserId() {
        return userId;
    }

}
