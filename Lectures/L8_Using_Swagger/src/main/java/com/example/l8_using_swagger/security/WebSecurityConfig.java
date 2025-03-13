package com.example.l8_using_swagger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Disable CSRF protection since token-based authentication is used
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        //publicly accessible endpoints - all swagger related
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**" , "/swagger-ui.html", "/webjars/**" ).permitAll()

                        //authorise access to the endpoints in v1 and v2 of the Books app
                        .requestMatchers("/api/v1/**", "/api/v2/**", "/api/books").permitAll()

                        //All other requests require authentication
                        .anyRequest().authenticated()
                )
                //Disable session management to ensure stateless authentication
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) //Disable default login form
                .logout(AbstractHttpConfigurer::disable);  //Disable default logout handler - dont want Spring to try and logout the user as i am doing it myself


        //Return the configured HttpSecurity object
        return http.build();
    }


}
