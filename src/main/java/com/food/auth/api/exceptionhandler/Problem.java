package com.food.auth.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

//veja RFC 7807
@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problem(@ApiModelProperty(example = "404", position = 1)
                      @JsonProperty("status") Integer status,
                      @ApiModelProperty(example = "https://food.com.br/recurso-nao-encontrado")
                      @JsonProperty("type") String type,
                      @ApiModelProperty(example = "Recurso não encontrado", position = 5)
                      @JsonProperty("title") String title,
                      @ApiModelProperty(example = "Não existe um cadastro de restaurante com código 10", position = 10)
                      @JsonProperty("detail") String detail,
                      @ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.", position = 15)
                      @JsonProperty("userMessage") String userMessage,
                      @ApiModelProperty(example = "2020-10-04T23:22:42.897164", position = 25)
                      @JsonProperty("timestamp") LocalDateTime timestamp,
                      @ApiModelProperty(example = "Objetos ou campos que geraram o erro (opcional)", position = 30)
                      @JsonProperty("fields") List<Field> fields) {
    public Problem(Integer status,
                   String type,
                   String title,
                   String detail,
                   String userMessage) {
        this(status, type, title, detail, userMessage, LocalDateTime.now(), null);
    }
}
