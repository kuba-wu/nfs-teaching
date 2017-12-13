package com.kubawach.nfs.teaching.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.kubawach.nfs.teaching.model.User;
import com.kubawach.nfs.teaching.service.SamlService;
import com.kubawach.nfs.teaching.service.UserService;

public class SamlSupportingTokenGranter extends ResourceOwnerPasswordTokenGranter {
    
    private final SamlService samlService;
    private final UserService userService;
    
    public SamlSupportingTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory, SamlService samlService, UserService userService) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
        
        this.samlService = samlService;
        this.userService = userService;
    }

    private boolean isSamlToken(TokenRequest tokenRequest) {
        return tokenRequest.getRequestParameters().containsKey("token");
    }
    
    private String getSamlToken(TokenRequest tokenRequest) {
        return tokenRequest.getRequestParameters().get("token");
    }
    
    private OAuth2Authentication authenticate(ClientDetails client, TokenRequest tokenRequest) {
        
        String token = getSamlToken(tokenRequest);
        String login = null;
        try {
            login = samlService.validateToken(token);
        } catch (IOException e) {
            throw new InvalidGrantException("Could not validate token: "+token, e);
        }
        if (login == null) {
            throw new InvalidGrantException("Illegal token: "+token);
        }
        User user = userService.saveNewUserIfNotPresent(login);
        Authentication auth = new UsernamePasswordAuthenticationToken(login, "N/A", SecurityUtils.toAuthorityList(user.getRoles()));
        
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);      
        return new OAuth2Authentication(storedOAuth2Request, auth);
    }
    
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        return (isSamlToken(tokenRequest) ? authenticate(client, tokenRequest) : super.getOAuth2Authentication(client, tokenRequest));
    }
}
