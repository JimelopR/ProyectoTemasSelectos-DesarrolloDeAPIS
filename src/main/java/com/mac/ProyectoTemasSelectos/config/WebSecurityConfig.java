/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    private final StrictHttpFirewall strictHttpFirewall;

    public WebSecurityConfig(StrictHttpFirewall strictHttpFirewall) {
        this.strictHttpFirewall = strictHttpFirewall;
    }

    // Configuración de seguridad usando HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable()
        .authorizeRequests()
            .requestMatchers("/loginUsuario", "/error", "/registrar-usuario").permitAll()
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .disable() 
            //.defaultSuccessUrl("/home", true) 
        .logout()
            .permitAll();

    return http.build();
    }
    
    // Definimos el PasswordEncoder como un bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Utilizamos BCryptPasswordEncoder
    }
    
    // Configuración global de CORS
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // Permite credenciales (cookies, autenticación HTTP)
        config.addAllowedOrigin("http://localhost:3000");  // Permite solicitudes de este origen
        config.addAllowedHeader("*");  // Permite todos los encabezados
        config.addAllowedMethod("*");  // Permite todos los métodos HTTP (GET, POST, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Aplica la configuración a todas las rutas
        return new CorsFilter(source);
    }
    
    
}
