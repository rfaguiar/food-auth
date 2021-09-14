package com.food.auth.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public record GrupoRequest(@ApiModelProperty(example = "Gerente", required = true)
                           @JsonProperty("nome")
                           @NotBlank String nome) {
}
