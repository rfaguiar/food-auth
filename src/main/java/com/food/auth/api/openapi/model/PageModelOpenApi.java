package com.food.auth.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("PageModel")
public class PageModelOpenApi {

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private Long size;
    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;
    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;
    @ApiModelProperty(example = "0", value = "Número de página (começa em 0)")
    private Long number;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
