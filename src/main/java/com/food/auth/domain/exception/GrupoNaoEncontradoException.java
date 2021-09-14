package com.food.auth.domain.exception;

import java.text.MessageFormat;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(MessageFormat.format("Não existe um cadastro de grupo com código {0}", id));
    }
}
