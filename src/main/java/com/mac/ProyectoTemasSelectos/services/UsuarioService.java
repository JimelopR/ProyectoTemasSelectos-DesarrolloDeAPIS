/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.services;


import com.mac.ProyectoTemasSelectos.models.UsuarioModel;
import com.mac.ProyectoTemasSelectos.repositories.UsuarioRepository;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author jimena
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //Para encriptar la contraseña antes de guardarña
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        // Encriptar la contraseña antes de guardarla
        String contraseñaEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contraseñaEncriptada);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
    
   
    // Método para generar una contraseña aleatoria
    public String generarContrasenaAleatoria() {
        int longitud = 8; // Longitud de la contraseña
        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder contrasena = new StringBuilder(longitud);
        
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            contrasena.append(caracteresPermitidos.charAt(indice));
        }
        return contrasena.toString();
    }
   

    public void imprimirUsuarios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            System.out.println("No se encontraron usuarios.");
        } else {
            for (UsuarioModel u : usuarios) {
                System.out.println("Usuario registrado: " + u.getCorreo());
            }
        }
    }
    
    
}
