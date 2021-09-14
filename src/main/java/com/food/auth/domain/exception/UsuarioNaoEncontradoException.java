package com.food.auth.domain.exception;

import java.text.MessageFormat;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de usuario com código {0}", id));
    }
}
