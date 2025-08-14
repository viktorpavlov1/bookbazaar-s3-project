package com.bookBazaar.configuration;

import com.bookBazaar.models.other.AccessToken;
import com.bookBazaar.services.interfaces.AccessTokenServiceINT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AccessTokenServiceINT accessTokenHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = requestTokenHeader.substring(7);
        try {
            AccessToken accessTokenDTO = accessTokenHandler.decode(accessToken);
            setupSpringSecurityContext(accessTokenDTO);
            filterChain.doFilter(request, response);
        } catch(ResponseStatusException e) {
            logger.error("Error validation access token", e);
            throw new SecurityException("Error validation access token");
        }
    }

    private void setupSpringSecurityContext(AccessToken accessToken){
        UserDetails userDetails = new User(accessToken.getSubject(), "", List.of(new SimpleGrantedAuthority(accessToken.getRole())));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(accessToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }
}
