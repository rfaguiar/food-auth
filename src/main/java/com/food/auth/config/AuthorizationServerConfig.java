package com.food.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RedisConnectionFactory redisConnectionFactory;
    private final JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, RedisConnectionFactory redisConnectionFactory, JwtKeyStoreProperties jwtKeyStoreProperties) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.redisConnectionFactory = redisConnectionFactory;
        this.jwtKeyStoreProperties = jwtKeyStoreProperties;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
            .withClient("food-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("WRITE", "READ")
                .accessTokenValiditySeconds(60*60*1)
                .refreshTokenValiditySeconds(60*60*2)
            .and()
                .withClient("food-analytics")
                .secret(passwordEncoder.encode("food123"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("WRITE", "READ")
                .redirectUris("http://localhost:8000", "http://aplicacao-cliente")
            .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("READ")
            .and()
                .withClient("webadmin")
                .authorizedGrantTypes("implicit")
                .scopes("WRITE", "READ")
                .redirectUris("http://aplicacao-cliente")
            .and()
                .withClient("checktoken")
                    .secret(passwordEncoder.encode("check123"))
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()")
//            .checkTokenAccess("permitAll()")
//            .tokenKeyAccess("permitAll()")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(List.of(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));
        endpoints
                .pathMapping("/oauth/check_token", "/oauth/introspect")
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints))
//                .tokenStore(redisTokenStore())
        ;
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        var aprovalStore = new TokenApprovalStore();
        aprovalStore.setTokenStore(tokenStore);
        return aprovalStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //chave simetrica
//        jwtAccessTokenConverter.setSigningKey("asgfasdgferqwtfdsgshgwstgqw4523refdsag234tg");

        //chave assimetrica
        var jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
        var keyStorePass = jwtKeyStoreProperties.getPassword();
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

        var keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    private TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        return new CompositeTokenGranter(List.of(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter()));
    }
}
