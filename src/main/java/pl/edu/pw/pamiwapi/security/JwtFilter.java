package pl.edu.pw.pamiwapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.edu.pw.pamiwapi.services.JwtService;
import pl.edu.pw.pamiwapi.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var token = getTokenFromRequest(request);

        if (token != null && jwtService.validateJwt(token)) {
            var username = jwtService.getUsernameFromJwt(token);
            var userDetails = userService.loadUserByUsername(username);
            var authToken = new UsernamePasswordAuthenticationToken(userDetails, "", new ArrayList<>()); // TODO: Add roles

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        return token != null && token.startsWith("Bearer") ? token.substring(7) : null;
    }
}
