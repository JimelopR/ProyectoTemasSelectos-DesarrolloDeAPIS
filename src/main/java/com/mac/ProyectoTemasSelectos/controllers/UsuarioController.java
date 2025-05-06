/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.controllers;

import com.mac.ProyectoTemasSelectos.models.UsuarioModel;
import com.mac.ProyectoTemasSelectos.services.CustomUserDetailsService;
import com.mac.ProyectoTemasSelectos.services.UsuarioService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jimena
 */

@RestController
public class UsuarioController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @PostMapping("/loginUsuario")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioModel usuarioModel) {
         System.out.println("Recibiendo petición de login");
          System.out.println("USUARIO ingresada: " + usuarioModel.getCorreo());
            System.out.println("Contraseña ingresada: " + usuarioModel.getPassword());
        try {
            // Intentamos cargar al usuario con el correo proporcionado
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(usuarioModel.getCorreo());            
            
            //Verificamos si la contraseña ingresada coincide con la almacenada en la base de datos
            if (customUserDetailsService.checkPassword(usuarioModel.getPassword(), userDetails.getPassword())) {
               
                    Map<String, Object> respuesta = new HashMap<>();
                        respuesta.put("message", "Login successful");

                    // Obtener la colección de GrantedAuthority
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                    String role = null;
                    if (!authorities.isEmpty()) {
                        role = authorities.iterator().next().getAuthority();
                    }

                    respuesta.put("role", role);
                    return ResponseEntity.ok(respuesta);
                                          
            } else {
                // Si las contraseñas no coinciden, retornamos un error
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
            }           
        } catch (UsernameNotFoundException e) {
            // Si hay alguna excepción, respondemos con un error de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
        }
  
    }      
}
