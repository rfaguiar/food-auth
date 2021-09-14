package com.food.auth.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record SenhaRequest(@ApiModelProperty(example = "123", required = true)
                           @JsonProperty("senhaAtual")
                           @NotBlank
                           String senhaAtual,
                           @ApiModelProperty(example = "123", required = true)
                           @JsonProperty("novaSenha")
                           @NotBlank
                           String novaSenha) {
}
