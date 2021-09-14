package com.food.auth.config;

import com.fasterxml.classmate.TypeResolver;
import com.food.auth.api.exceptionhandler.Problem;
import com.food.auth.api.openapi.model.GruposModelOpenApi;
import com.food.auth.api.openapi.model.LinksModelOpenApi;
import com.food.auth.api.openapi.model.PageableModelOpenApi;
import com.food.auth.api.openapi.model.PermissoesModelOpenApi;
import com.food.auth.api.openapi.model.UsuariosModelOpenApi;
import com.food.auth.api.v1.model.response.GrupoResponse;
import com.food.auth.api.v1.model.response.PermissaoResponse;
import com.food.auth.api.v1.model.response.UsuarioResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Collections;
import java.util.List;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class OpenApiConfig {

    public static final String TAG_GRUPO = "Grupos";
    public static final String TAG_USUARIOS = "Usuários";
    public static final String TAG_PERMISSOES = "Permissões";

    @Bean
    public Docket apiDocketV1() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.food.auth"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(PagedModel.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoResponse.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoResponse.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioResponse.class),
                        UsuariosModelOpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        File.class, Resource.class, InputStream.class, Sort.class)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfoV1())
                .tags(createTag(TAG_GRUPO, "Gerencia os grupos de usuários"),
                        createTag(TAG_USUARIOS, "Gerencia os usuários"),
                        createTag(TAG_PERMISSOES, "Gerencia as permissões"));
    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("FoodOAuth")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();
        return SecurityContext.builder()
                .securityReferences(List.of(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("FoodOAuth")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private List<AuthorizationScope> scopes() {
        return List.of(
                new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita")
        );
    }

    private List<GrantType> grantTypes() {
        return List.of(
                new ResourceOwnerPasswordCredentialsGrant("/oauth/token")
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private List<Response> globalGetResponseMessages() {
        return List.of(
                new ResponseBuilder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .description("Erro interno do servidor")
                    .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
                );
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Rogerio Aguiar", "https://github.com/rfaguiar", "rfaguiar1@gmail.com"))
                .build();
    }


    private Tag createTag(String name, String description) {
        return new Tag(name, description);
    }
}
