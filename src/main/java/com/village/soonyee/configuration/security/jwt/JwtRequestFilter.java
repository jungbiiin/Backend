package com.village.soonyee.configuration.security.jwt;

import com.village.soonyee.configuration.security.auth.MyUserDetailsService;
import com.village.soonyee.exception.ErrorCode;
import com.village.soonyee.exception.exception.InvalidTokenException;
import com.village.soonyee.exception.exception.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = req.getHeader("Authorization");
        // Access Token이 null이면 검증할 필요가 없다.
        if (accessToken != null) {
            String userEmail = accessTokenExtractEmail(accessToken);

            if(userEmail != null) registerUserinfoInSecurityContext(userEmail, req);
        }
        filterChain.doFilter(req, res);
    }

    private String accessTokenExtractEmail(String accessToken) {
        try {
            return jwtUtil.getUserEmail(accessToken);
        } catch (JwtException | IllegalArgumentException e ) {
            throw new InvalidTokenException("Invalid access token", ErrorCode.INVALID_TOKEN);
        }
    }

    private void registerUserinfoInSecurityContext(String userEmail, HttpServletRequest req) {
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (NullPointerException e) {
            throw new UserNotFoundException("Can't find user", ErrorCode.USER_NOT_FOUND);
        }
    }
}