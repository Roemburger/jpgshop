package com.iprwc.jpgshop.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.iprwc.jpgshop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtToken jwtToken;

    public JwtFilter(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {
        String header = request.getHeader("Auth");
        if (header != null && !header.isBlank() && header.startsWith("Bearer ")) {
            String jwt = header.substring(7);
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT token in bearer header is invalid");
                return;
            } else {
                try {
                    String email = jwtToken.verifyTokenAndGetClaim(jwt);
                    UserDetails userDetails = userService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(upaToken);
                    }
                } catch (JWTVerificationException exception) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Auth");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
