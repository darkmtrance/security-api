package com.matomaylla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("Authorization");

        LOGGER.info("Request: {} {} Headers: {}", request.getMethod(), request.getURI(), headers);
        if (token != null) {
            LOGGER.info("Token: {}", token);
        }
        LOGGER.debug("Request body: {}", new String(body));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        LOGGER.info("Response status code: {}", response.getStatusCode());
        LOGGER.info("Response headers: {}", response.getHeaders());
        LOGGER.debug("Response body: {}", response.getBody());
    }

}