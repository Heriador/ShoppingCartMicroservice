package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.security;


import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.configuration.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class SecurityBeanInjector {

    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityBeanInjector(CustomUserDetailsService customUserDetailsService,
                                @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.customUserDetailsService = customUserDetailsService;
        this.exceptionResolver = exceptionResolver;
    }



    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(customUserDetailsService, exceptionResolver);
    }
}
