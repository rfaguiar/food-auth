package com.food.auth.api.openapi.controller;

import com.food.auth.api.exceptionhandler.Problem;
import com.food.auth.api.v1.model.response.GrupoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import static com.food.auth.config.OpenApiConfig.TAG_USUARIOS;


@Api(tags = TAG_USUARIOS)
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    CollectionModel<GrupoResponse> listar(@ApiParam(value = "ID do usuário", example = "1", required = true)
                               Long usuarioId);

    @ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do usuário", example = "1", required = true)
                     Long usuarioId,
                     @ApiParam(value = "ID do grupo", example = "1", required = true)
                     Long grupoId);

    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do usuário", example = "1", required = true)
                  Long usuarioId,
                                  @ApiParam(value = "ID do grupo", example = "1", required = true)
                  Long grupoId);
}
