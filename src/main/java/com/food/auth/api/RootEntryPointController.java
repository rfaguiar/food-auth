package com.food.auth.api;

import com.food.auth.api.v1.RootEntryPointControllerV1;
import com.food.auth.api.v1.model.response.RootEntryPointResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ApiIgnore
@RestController
@RequestMapping("/")
public class RootEntryPointController {


    @GetMapping
    public RootEntryPointResponse root() {
        return new RootEntryPointResponse()
                .add(linkTo(RootEntryPointControllerV1.class).withRel("v1"));
    }
}
