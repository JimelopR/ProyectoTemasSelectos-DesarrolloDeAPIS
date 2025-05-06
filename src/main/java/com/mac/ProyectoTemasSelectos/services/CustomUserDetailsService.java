/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.services;

/**
 *
 * @author jimena
 */

import com.mac.ProyectoTemasSelectos.models.UsuarioModel;
import com.mac.ProyectoTemasSelectos.repositories.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {  
       
        Optional<UsuarioModel> usuarioOpt = Optional.empty();  // Iniciar como Optional vacío
        try {
        // Intentamos obtener el usuario por correo
            usuarioOpt = usuarioRepository.findByCorreo(correo);
            if (usuarioOpt.isPresent()) {
                System.out.println("Usuario encontrado: " + usuarioOpt.get().getCorreo());
            } else {
                System.out.println("Usuario con correo " + correo + " no encontrado.");
            }
        } catch (Exception e) {
            // Log de la excepción para entender el problema
            System.err.println("Error al buscar el usuario con correo " + correo + ": " + e.getMessage());
        }
        

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        UsuarioModel usuario = usuarioOpt.get();
        
        String rol = "";
        switch (usuario.getIdTipoUsuario().intValue()) {
            case 1:
                rol = "ADMINISTRADOR";
                break;
            case 2:
                rol = "EVALUADOR";
                break;
            case 3:
                rol = "EVALUADO";
                break;
            default:
                // Manejar un caso por defecto o lanzar una excepción
                rol = "USUARIO";
                break;
        }

        // Devuelve un objeto UserDetails
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .authorities(new SimpleGrantedAuthority(rol))            
                .build();
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
