package com.tv.tvapi.config;

import com.tv.tvapi.jwt.AuthorizationFilter;
import com.tv.tvapi.jwt.JwtAuthFilter;
import com.tv.tvapi.service.JwtService;
import com.tv.tvapi.service.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(authenticationManagerBean(), jwtService);
        jwtAuthFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(jwtAuthFilter);
        http.addFilterBefore(new AuthorizationFilter(userDetailService, jwtService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider getAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Configuration
    public class CorsConfig {
        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
//            com.tv.socket.config.setAllowCredentials(true);
            config.addAllowedOrigin("http://localhost:3000");
            config.addAllowedHeader("*");
            config.addAllowedMethod("POST");
            config.addAllowedMethod("GET");
            config.addAllowedMethod("PATCH");
            config.addAllowedMethod("DELETE");
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
    }



}
