package com.food.auth.service;


import com.food.auth.domain.model.Permissao;

public interface PermissaoService {

    Permissao buscarOuFalhar(Long permissaoId);
}
