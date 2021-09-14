package com.food.auth.api.v1;

import com.food.auth.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.food.auth.api.v1.model.assembler.FoodLinks;
import com.food.auth.api.v1.model.assembler.GrupoResponseAssembler;
import com.food.auth.api.v1.model.response.GrupoResponse;
import com.food.auth.config.FoodSecurity;
import com.food.auth.security.CheckSecurity;
import com.food.auth.service.UsuarioService;
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
@RequestMapping(value = "/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final UsuarioService usuarioService;
    private final GrupoResponseAssembler grupoResponseAssembler;
    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public UsuarioGrupoController(UsuarioService usuarioService,
                                  GrupoResponseAssembler grupoResponseAssembler,
                                  FoodLinks foodLinks,
                                  FoodSecurity foodSecurity) {
        this.usuarioService = usuarioService;
        this.grupoResponseAssembler = grupoResponseAssembler;
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoResponse> listar(@PathVariable Long usuarioId) {
        CollectionModel<GrupoResponse> gruposResponse = grupoResponseAssembler.toCollectionModel(usuarioService.buscarGruposPorUsuarioId(usuarioId))
                .removeLinks();
        if (foodSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposResponse.add(foodLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
            gruposResponse.getContent()
                    .forEach(grupoModel -> grupoModel.add(foodLinks.linkToUsuarioGrupoDesassociacao(
                            usuarioId, grupoModel.getId(), "desassociar")));
        }
        return gruposResponse;
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }
}
