package com.food.auth.service.model;

import com.food.auth.domain.model.Usuario;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AuthUser extends User {

    private final String fullName;

    public AuthUser(Usuario usuario) {
        super(usuario.email(), usuario.senha(), Collections.emptyList());
        this.fullName = usuario.nome();
    }

    public String getFullName() {
        return fullName;
    }
}
