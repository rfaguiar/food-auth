package com.food.auth.api.v1.model.response;

import com.food.auth.domain.model.Permissao;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
public class PermissaoResponse extends RepresentationModel<PermissaoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "CONSULTAR_COZINHAS")
    private String nome;
    @ApiModelProperty(example = "Permite consultar cozinhas")
    private String descricao;

    public PermissaoResponse(Permissao permissao) {
        this(permissao.getId(), permissao.getNome(), permissao.getDescricao());
    }

    public PermissaoResponse(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
