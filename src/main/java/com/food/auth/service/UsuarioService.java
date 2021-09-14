package com.food.auth.service;

import com.food.auth.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.auth.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.auth.domain.model.Grupo;
import com.food.auth.domain.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();

    Usuario buscar(Long id);

    Usuario cadastrar(UsuarioComSenhaRequest usuario);

    Usuario atualizar(Long id, UsuarioSemSenhaRequest usuario);

    void alterarSenha(Long id, String senhaAtual, String novaSenha);

    List<Grupo> buscarGruposPorUsuarioId(Long usuarioId);

    void desassociarGrupo(Long usuarioId, Long grupoId);

    void associarGrupo(Long usuarioId, Long grupoId);
}
