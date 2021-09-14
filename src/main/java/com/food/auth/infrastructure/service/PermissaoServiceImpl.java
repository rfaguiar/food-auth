package com.food.auth.infrastructure.service;

import com.food.auth.domain.exception.PermissaoNaoEncontradaException;
import com.food.auth.domain.model.Permissao;
import com.food.auth.domain.repository.PermissaoRepository;
import com.food.auth.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoServiceImpl implements PermissaoService {

    private final PermissaoRepository permissaoRepository;

    @Autowired
    public PermissaoServiceImpl(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    @Override
    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
