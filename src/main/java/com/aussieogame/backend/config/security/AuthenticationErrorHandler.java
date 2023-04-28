package com.aussieogame.backend.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectToJsonMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        String responseBody = createResponseBody();

        prepareResponse(response, responseBody);
    }

    // TODO rework error responses
    private String createResponseBody() throws IOException {
//        ErrorResponse requiresAuthenticationResponse = ErrorResponse.from("REQUIRES AUTHENTICATION");

//        return objectToJsonMapper.writeValueAsString(requiresAuthenticationResponse);
        return "REQUIRED AUTHENTICATION";
    }

    private void prepareResponse(HttpServletResponse response, String body) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
        response.flushBuffer();
    }
}
