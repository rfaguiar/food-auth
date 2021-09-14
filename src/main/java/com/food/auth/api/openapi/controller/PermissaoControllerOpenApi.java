package com.food.auth.api.openapi.controller;

import com.food.auth.api.v1.model.response.PermissaoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

import static com.food.auth.config.OpenApiConfig.TAG_PERMISSOES;


@Api(tags = TAG_PERMISSOES)
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoResponse> listar();
}
