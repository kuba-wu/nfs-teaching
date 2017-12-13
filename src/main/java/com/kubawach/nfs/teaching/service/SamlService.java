package com.kubawach.nfs.teaching.service;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SamlService {

    private static final Logger log = Logger.getLogger(SamlService.class);
    
    private static final String VALIDATION_REQUEST = 
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<SOAP-ENV:Header/>"+
            "<SOAP-ENV:Body>"+
            "<samlp:Request"+
            "xmlns:samlp=\"urn:oasis:names:tc:SAML:1.0:protocol\""+
            "MajorVersion=\"1\""+
            "MinorVersion=\"1\""+
            "RequestID=\"{0}\""+
            "IssueInstant=\"{1}\">"+
            "<samlp:AssertionArtifact>"+
            "{2}"+
            "</samlp:AssertionArtifact>"+
            "</samlp:Request>"+
            "</SOAP-ENV:Body>"+
        "</SOAP-ENV:Envelope>";
        
    @Autowired private HttpServletRequest request;
    @Value("${sso-login-url}") String ssoLoginUrl;
    @Value("${sso-logout-url}") String ssoLogoutUrl;
    
    public void logout() throws IOException {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(ssoLogoutUrl);
        client.executeMethod(get);
    }
    
    public String validateToken(String token) throws IOException {
        
        HttpClient client = new HttpClient();
        URI contextUrl = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath());
        String url = ssoLoginUrl+contextUrl.toString();
        log.info("SAML request to: "+url);
        PostMethod post = new PostMethod(url);

        String requestContent = MessageFormat.format(VALIDATION_REQUEST, new Date().getTime(), new Date().toString(), token);
        post.setRequestEntity(new StringRequestEntity(requestContent, null, null));
        client.executeMethod(post);
        String response = post.getResponseBodyAsString();
        log.debug("Got SAML response: "+response);
        if (response != null) {
            int start = response.indexOf("<NameIdentifier>");
            int end = response.indexOf("</NameIdentifier>");
            if (start != -1 && end != -1) {
                return response.substring(start+"<NameIdentifier>".length(), end);
            }
        }
        return null;
    }
}
