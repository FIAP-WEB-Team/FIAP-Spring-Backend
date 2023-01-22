package br.com.fiap.spring.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtUserDetailService jwtUserDetailService,
                            JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailService = jwtUserDetailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeaderToken = request.getHeader("Authorization");
        String username = null;
        if (authorizationHeaderToken != null && authorizationHeaderToken.startsWith("Bearer ")) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(authorizationHeaderToken);
            } catch (IllegalArgumentException | ExpiredJwtException illegal) {
                logger.info(illegal.getMessage());
            }
        } else {
            logger.warn("Token " + authorizationHeaderToken + " not valid");
        }
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
