package com.food.auth.api.v1.model.response;

import com.food.auth.domain.model.Usuario;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
public class UsuarioResponse extends RepresentationModel<UsuarioResponse> {

    @ApiModelProperty(example = "1")
    Long id;
    @ApiModelProperty(example = "João da Silva")
    String nome;
    @ApiModelProperty(example = "joao.ger@food.com.br")
    String email;

    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public UsuarioResponse(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
