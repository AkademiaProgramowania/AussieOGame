package com.aussieogame.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseHeadersFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletResponse httpResponse = castToHttpResponse(response);

        setHeaders(httpResponse);

        //Chain of Responsibility pattern
        chain.doFilter(request, response);
    }

    private HttpServletResponse castToHttpResponse(ServletResponse response) throws IOException {
        if (isNotHttpResponse(response)) {
            throw new IOException("Unsupported Servlet Response class: " + response.getClass().getName());
        }
        return (HttpServletResponse) response;
    }

    private boolean isNotHttpResponse(ServletResponse response) {
        return !(response instanceof HttpServletResponse);
    }

    private void setHeaders(HttpServletResponse httpResponse) {
        httpResponse.setIntHeader("X-XSS-Protection", 0);
        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        httpResponse.setHeader("X-Frame-Options", "deny");
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("Content-Security-Policy", "default-src 'self'; frame-ancestors 'none';");
        httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate");
        httpResponse.setHeader(HttpHeaders.PRAGMA, "no-cache");
        httpResponse.setIntHeader(HttpHeaders.EXPIRES, 0);
    }
}
