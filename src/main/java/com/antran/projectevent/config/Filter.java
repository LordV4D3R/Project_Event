package com.antran.projectevent.config;

import com.antran.projectevent.constant.enums.AccountRole;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.model.dto.TokenData;
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

    private final List<String> ADMIN_PERMISSON = List.of(
            "/api/accounts"
    );
    public boolean isPermitted(String uri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        //check if the uri is in the list of permitted uri
        return AUTHEN_PERMISSON.stream().anyMatch(p -> antPathMatcher.match(p, uri));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        boolean isPermitted = isPermitted(requestURI);

        if (isPermitted) {
            filterChain.doFilter(request, response);
        } else {
            String token = getToken(request);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid Authorization header.");
                return;
            }

            TokenData tokenData = jwtUtil.extractTokenData(token);

            try {
                if (jwtUtil.validateToken(token)) {
                    AccountRole role = jwtUtil.extractRole(token);
                    if (AccountRole.ADMIN.equals(role) || (AccountRole.MEMBER.equals(role) && !ADMIN_PERMISSON.contains(requestURI))) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(tokenData, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Access denied.");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid or expired token.");
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
            } catch (MalformedInputException | IllegalIdentifierException e) {
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
