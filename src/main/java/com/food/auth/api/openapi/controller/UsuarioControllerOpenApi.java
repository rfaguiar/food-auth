package com.food.auth.api.openapi.controller;

import com.food.auth.api.exceptionhandler.Problem;
import com.food.auth.api.v1.model.request.SenhaRequest;
import com.food.auth.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.auth.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.auth.api.v1.model.response.UsuarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import static com.food.auth.config.OpenApiConfig.TAG_USUARIOS;

@Api(tags = TAG_USUARIOS)
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioResponse> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioResponse porId(@ApiParam(value = "ID do usuário", example = "1", required = true)
                           Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UsuarioResponse cadastrar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
                                      UsuarioComSenhaRequest usuario);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioResponse atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true)
                              Long usuarioId,
                              @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                                      required = true)
                                      UsuarioSemSenhaRequest usuario);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true)
                      Long usuarioId,
                      @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true)
                              SenhaRequest senha);
}
