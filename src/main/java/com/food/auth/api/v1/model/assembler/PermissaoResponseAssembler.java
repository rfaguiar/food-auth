package com.food.auth.api.v1.model.assembler;

import com.food.auth.api.v1.model.response.PermissaoResponse;
import com.food.auth.config.FoodSecurity;
import com.food.auth.domain.model.Permissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoResponseAssembler implements RepresentationModelAssembler<Permissao, PermissaoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public PermissaoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public PermissaoResponse toModel(Permissao permissao) {
        return new PermissaoResponse(permissao);
    }

    @Override
    public CollectionModel<PermissaoResponse> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoResponse> permissaoResponses = RepresentationModelAssembler.super.toCollectionModel(entities);
        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            permissaoResponses.add(foodLinks.linkToPermissoes());
        }
        return permissaoResponses;
    }
}
