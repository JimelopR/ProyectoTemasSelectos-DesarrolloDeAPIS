/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author jlopez
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    
        registry.addMapping("/**") // Permite CORS para todos los endpoints
                .allowedOrigins("http://localhost:3000") // Permite solicitudes de localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                .allowedHeaders("*"); // Permite todos los headers
    }
}
