package com.shivsundar.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
                .principal("internal")
                .build())
                .getAccessToken()
                .getTokenValue();
        request.getHeaders().add("Authorization", "Bearer "+token);
        logger.info("token {}", token);
        return execution.execute(request, body);
    }
}
