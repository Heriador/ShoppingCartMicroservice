package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.security.filter;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.security.CustomUserDetailsService;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.util.AuthenticationConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AuthenticationConstants.AUTHORIZATION_HEADER);


        if(authHeader == null || !authHeader.startsWith(AuthenticationConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try{

            String jwt = authHeader.split(AuthenticationConstants.TOKEN_SPLITTER)[1];

            UserDetails user = userDetailsService.loadUserByUsername(jwt);



            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,jwt, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
        }
        catch (Exception e){
            exceptionResolver.resolveException(request, response, null, e);
        }


    }
}
