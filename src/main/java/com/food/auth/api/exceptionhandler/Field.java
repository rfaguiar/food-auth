package com.food.auth.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.FieldError;

public record Field(@ApiModelProperty(example = "preco")
                    @JsonProperty("name") String name,
                    @ApiModelProperty(example = "O preço é obrigatório")
                    @JsonProperty("userMessage") String userMessage) {
    public Field(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
