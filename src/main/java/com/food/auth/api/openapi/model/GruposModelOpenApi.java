package com.food.auth.api.openapi.model;

import com.food.auth.api.v1.model.response.GrupoResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
public class GruposModelOpenApi {

    private GrupoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    public class GrupoEmbeddedModelOpenApi {

        private List<GrupoResponse> grupos;

        public List<GrupoResponse> getGrupos() {
            return grupos;
        }

        public void setGrupos(List<GrupoResponse> grupos) {
            this.grupos = grupos;
        }
    }

    public GrupoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(GrupoEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
