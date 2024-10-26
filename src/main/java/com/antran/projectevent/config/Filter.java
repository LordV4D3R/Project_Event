package com.antran.projectevent.config;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final List<String> AUTHEN_PERMISSON = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "swagger-resources/**",
            "/api/auth/login",
            "/api/auth/register"
    );

    public boolean isPermitted(String uri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //check if the uri is in the list of permitted uri
        return AUTHEN_PERMISSON.stream().anyMatch(p -> antPathMatcher.match(p, uri));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isPermitted = isPermitted(request.getRequestURI());
        if (isPermitted) {
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);
            if (token == null) {
                // Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid Authorization header.");
                return;
            }
            try {
                String userName = jwtUtil.extractUsername(token);
                if (jwtUtil.validateToken(token, userName)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userName, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // Authorized
                    filterChain.doFilter(request, response);
                } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                }
            } catch (ExpiredJwtException e) {
                // Token expired
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
            } catch (MalformedInputException | IllegalIdentifierException e) {
                // Invalid token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
            }

        }
    }

    public String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) return null;
        return header.replace("Bearer ", "");
    }
}
