package com.ale.blog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RedirectAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String ACCEPT_HEADER = "Accept";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (isHtmlRequest(request)) {
            response.sendRedirect("/login");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access is not allowed");
        }
    }

    private boolean isHtmlRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader(ACCEPT_HEADER);
        List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);
        return acceptedMediaTypes.contains(MediaType.TEXT_HTML);
    }
}
