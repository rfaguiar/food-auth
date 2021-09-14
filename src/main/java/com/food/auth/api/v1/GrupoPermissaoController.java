package com.food.auth.api.v1;

import com.food.auth.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.food.auth.api.v1.model.assembler.FoodLinks;
import com.food.auth.api.v1.model.assembler.PermissaoResponseAssembler;
import com.food.auth.api.v1.model.response.PermissaoResponse;
import com.food.auth.config.FoodSecurity;
import com.food.auth.security.CheckSecurity;
import com.food.auth.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final GrupoService grupoService;
    private final PermissaoResponseAssembler permissaoResponseAssembler;
    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public GrupoPermissaoController(GrupoService grupoService, PermissaoResponseAssembler permissaoResponseAssembler, FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.grupoService = grupoService;
        this.permissaoResponseAssembler = permissaoResponseAssembler;
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoResponse> listar(@PathVariable Long grupoId) {
        CollectionModel<PermissaoResponse> permissoesResponse = permissaoResponseAssembler.toCollectionModel(grupoService.buscarPermissoesOuFalhar(grupoId))
                .removeLinks()
                .add(foodLinks.linkToGrupoPermissoes(grupoId));
        if (foodSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesResponse.add(foodLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
            permissoesResponse.getContent().forEach(permissaoModel -> permissaoModel.add(
                    foodLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar")));
        }
        return permissoesResponse;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
