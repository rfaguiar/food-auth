package com.food.auth.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UsuarioComSenhaRequest(@ApiModelProperty(example = "João da Silva", required = true)
                                     @JsonProperty("nome")
                                     @NotBlank
                                     String nome,
                                     @ApiModelProperty(example = "123", required = true)
                                     @JsonProperty("senha")
                                     @NotBlank
                                     String senha,
                                     @ApiModelProperty(example = "joao.ger@algafood.com.br", required = true)
                                     @JsonProperty("email")
                                     @NotBlank
                                     @Email
                                     String email) {
}
