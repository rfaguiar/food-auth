package com.food.auth.api.v1.model.assembler;

import com.food.auth.api.v1.UsuarioController;
import com.food.auth.api.v1.model.response.UsuarioResponse;
import com.food.auth.config.FoodSecurity;
import com.food.auth.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioResponseAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public UsuarioResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(UsuarioController.class, UsuarioResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public UsuarioResponse toModel(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuario)
                .add(foodLinks.linkToUsuario(usuario.getId()));
        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            usuarioResponse
                    .add(foodLinks.linkToUsuarios("usuarios"))
                    .add(foodLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }
        return usuarioResponse;
    }

    @Override
    public CollectionModel<UsuarioResponse> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios)
                .add(foodLinks.linkToUsuarios());
    }
}
