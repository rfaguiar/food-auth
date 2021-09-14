package com.food.auth.api.v1;

import com.food.auth.api.openapi.controller.GrupoControllerOpenApi;
import com.food.auth.api.v1.model.assembler.GrupoResponseAssembler;
import com.food.auth.api.v1.model.request.GrupoRequest;
import com.food.auth.api.v1.model.response.GrupoResponse;
import com.food.auth.security.CheckSecurity;
import com.food.auth.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoService grupoService;
    private final GrupoResponseAssembler grupoResponseAssembler;

    @Autowired
    public GrupoController(GrupoService grupoService, GrupoResponseAssembler grupoResponseAssembler) {
        this.grupoService = grupoService;
        this.grupoResponseAssembler = grupoResponseAssembler;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoResponse> listar() {
        return grupoResponseAssembler.toCollectionModel(grupoService.listar());
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{grupoId}")
    public GrupoResponse buscar(@PathVariable Long grupoId) {
        return grupoResponseAssembler.toModel(grupoService.buscar(grupoId));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponse cadastrar(@RequestBody @Valid GrupoRequest grupo) {
        return grupoResponseAssembler.toModel(grupoService.cadastrar(grupo));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public GrupoResponse atualizar(@PathVariable Long grupoId,
                                   @RequestBody @Valid GrupoRequest dto) {
        return grupoResponseAssembler.toModel(grupoService.atualizar(grupoId, dto));
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.remover(grupoId);
    }
}

