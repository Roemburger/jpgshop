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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
                return;
            } else {
                try {
                    String email = jwtToken.findUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exc) {
                    response.sendError(401, exc.getMessage());
                    return;
                }
            }
        }

//        try {
//            String token = parseJwt(request);
//            if (token != null && jwtToken.verifyJwtToken(token)) {
//                String email = jwtToken.findUserNameFromJwtToken(token);
//                UserDetails userDetails = userService.loadUserByUsername(email);
//                UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(upaToken);
//            }
//        } catch (Exception ignored) {
//        }

        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
