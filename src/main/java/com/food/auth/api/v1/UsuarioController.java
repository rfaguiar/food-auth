package com.food.auth.api.v1;

import com.food.auth.api.openapi.controller.UsuarioControllerOpenApi;
import com.food.auth.api.v1.model.assembler.UsuarioResponseAssembler;
import com.food.auth.api.v1.model.request.SenhaRequest;
import com.food.auth.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.auth.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.auth.api.v1.model.response.UsuarioResponse;
import com.food.auth.security.CheckSecurity;
import com.food.auth.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioService usuarioService;
    private final UsuarioResponseAssembler usuarioResponseAssembler;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioResponseAssembler usuarioResponseAssembler) {
        this.usuarioService = usuarioService;
        this.usuarioResponseAssembler = usuarioResponseAssembler;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioResponse> listar() {
        return usuarioResponseAssembler.toCollectionModel(usuarioService.listar());
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{usuarioId}")
    public UsuarioResponse porId(@PathVariable Long usuarioId) {
        return usuarioResponseAssembler.toModel(usuarioService.buscar(usuarioId));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@RequestBody @Valid UsuarioComSenhaRequest usuario) {
        return usuarioResponseAssembler.toModel(usuarioService.cadastrar(usuario));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{usuarioId}")
    public UsuarioResponse atualizar(@PathVariable Long usuarioId,
                                     @RequestBody @Valid UsuarioSemSenhaRequest usuario) {
        return usuarioResponseAssembler.toModel(usuarioService.atualizar(usuarioId, usuario));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaRequest senha) {
        usuarioService.alterarSenha(usuarioId, senha.senhaAtual(), senha.novaSenha());
    }
}
