package com.food.auth.api.v1;

import com.food.auth.api.openapi.controller.PermissaoControllerOpenApi;
import com.food.auth.api.v1.model.assembler.PermissaoResponseAssembler;
import com.food.auth.api.v1.model.response.PermissaoResponse;
import com.food.auth.domain.model.Permissao;
import com.food.auth.domain.repository.PermissaoRepository;
import com.food.auth.security.CheckSecurity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoResponseAssembler permissaoResponseAssembler;

    public PermissaoController(PermissaoRepository permissaoRepository, PermissaoResponseAssembler permissaoResponseAssembler) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoResponseAssembler = permissaoResponseAssembler;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoResponse> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        return permissaoResponseAssembler.toCollectionModel(todasPermissoes);
    }

}
