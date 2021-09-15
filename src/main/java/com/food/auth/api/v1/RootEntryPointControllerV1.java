package com.food.auth.api.v1;

import com.food.auth.api.v1.model.assembler.FoodLinks;
import com.food.auth.api.v1.model.response.RootEntryPointResponse;
import com.food.auth.config.FoodSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/v1")
public class RootEntryPointControllerV1 {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public RootEntryPointControllerV1(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @GetMapping
    public RootEntryPointResponse root() {
        var rootEntryPointResponse = new RootEntryPointResponse();

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointResponse
                    .add(foodLinks.linkToGrupos("grupos"))
                    .add(foodLinks.linkToUsuarios("usuarios"))
                    .add(foodLinks.linkToPermissoes("permissoes"));
        }

        return rootEntryPointResponse;
    }
}
