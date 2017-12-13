package com.kubawach.nfs.teaching.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.security.SamlSupportingTokenGranter;
import com.kubawach.nfs.teaching.service.SamlService;
import com.kubawach.nfs.teaching.service.UserService;

@Configuration
public class OAuth2ServerConfiguration {
    
    /**
     * Key used to sign JWT keys - needs to be the same on both sides (auth server, resource server).
     */
    public static final String JWT_MAC_SIGNING_KEY = "j2wwSFs5Sra43ar4t3vmsA4w3wRAer4";
    /**
     * Client ID used to obtain OAUTH2 tokens from the auth server.
     */
    public static final String OAUTH_CLIENT_ID = "nfs-app";
    /**
     * Secret (password) to be used along with the client ID.
     */
    public static final String OAUTH_SECRET = "mySecretOAuthSecret";
    /**
     * OAuth2 token service endpoint.
     */
    public static final String OAUTH_TOKEN_ENDPOINT = "/oauth/token";
    
    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        
        @Bean
        public JwtAccessTokenConverter tokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(JWT_MAC_SIGNING_KEY);
            return converter;
        }
        
        public TokenStore tokenStore() {
            return new JwtTokenStore(tokenConverter());
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
                        .antMatchers("/components/*", "/css/*", "/externals/*", "/images/*", "/js/*", "/index.html", "/api/task")
                            .permitAll()
                        .antMatchers("/*")
                            .authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired private AuthenticationManager authenticationManager;
        @Autowired private SamlService samlService;
        @Autowired private UserService userService;
        
        @Autowired private ClientDetailsService clientDetailsService;
        
        @Bean
        public JwtAccessTokenConverter tokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(JWT_MAC_SIGNING_KEY);
            return converter;
        }

        private TokenStore tokenStore() {
            return new JwtTokenStore(tokenConverter());
        }       
        
        private static TokenEnhancer tokenEnhancer() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(JWT_MAC_SIGNING_KEY);
            return converter;
        }
        
        private TokenGranter tokenGranter(AuthorizationServerTokenServices tokenServices) {
            return new SamlSupportingTokenGranter(authenticationManager, tokenServices, clientDetailsService, new DefaultOAuth2RequestFactory(clientDetailsService), samlService, userService);
        }
        
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                .prefix("/api")
                .tokenEnhancer(tokenEnhancer())
                .tokenStore(tokenStore())
                .tokenGranter(tokenGranter(endpoints.getTokenServices()))
                .authenticationManager(authenticationManager);
        }

        
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                .inMemory()
                .withClient(OAUTH_CLIENT_ID)
                .scopes("read", "write")
                .authorities(Role.ADMIN)
                .authorizedGrantTypes("password")
                .secret(OAUTH_SECRET)
                .accessTokenValiditySeconds(3600);
        }     
    }
}
