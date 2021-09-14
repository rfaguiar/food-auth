package com.food.auth.api.openapi.controller;

import com.food.auth.api.exceptionhandler.Problem;
import com.food.auth.api.v1.model.request.GrupoRequest;
import com.food.auth.api.v1.model.response.GrupoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import static com.food.auth.config.OpenApiConfig.TAG_GRUPO;


@Api(tags = TAG_GRUPO)
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoResponse> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoResponse buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoResponse cadastrar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoRequest grupo);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoResponse atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
                            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GrupoRequest dto);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);
}
