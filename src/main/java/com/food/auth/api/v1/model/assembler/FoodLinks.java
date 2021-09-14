package com.food.auth.api.v1.model.assembler;

import com.food.auth.api.v1.GrupoController;
import com.food.auth.api.v1.GrupoPermissaoController;
import com.food.auth.api.v1.PermissaoController;
import com.food.auth.api.v1.UsuarioController;
import com.food.auth.api.v1.UsuarioGrupoController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FoodLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    private static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM));


    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .porId(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }




    public Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }

    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .listar(grupoId)).withRel(rel);
    }

    public Link linkToGrupo(Long grupoId) {
        return linkToGrupo(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupo(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoController.class)
                .buscar(grupoId)).withRel(rel);
    }

    public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null)).withRel(rel);
    }

    public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId)).withRel(rel);
    }

    public Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    public Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }
    public Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null)).withRel(rel);
    }

    public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, permissaoId)).withRel(rel);
    }

}
