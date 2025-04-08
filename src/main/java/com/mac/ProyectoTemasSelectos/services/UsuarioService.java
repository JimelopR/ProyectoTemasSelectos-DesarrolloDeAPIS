/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.services;


import com.mac.ProyectoTemasSelectos.models.UsuarioModel;
import com.mac.ProyectoTemasSelectos.repositories.UsuarioRepository;
import java.util.Optional;
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

    public Optional<UsuarioModel> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        // Encriptar la contraseña antes de guardarla
        String contraseñaEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contraseñaEncriptada);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
    
    // Método para obtener un usuario por ID
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para actualizar un usuario
    public UsuarioModel actualizarUsuario(Long id, UsuarioModel usuarioActualizado) {
        if (usuarioRepository.existsById(id)) {
            usuarioActualizado.setId(id); 
            return usuarioRepository.save(usuarioActualizado);
        }
        return null; // Si no se encuentra el usuario
    }

    // Método para eliminar un usuario por ID
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;  // El usuario fue eliminado correctamente
        }
        return false; // Si no se encuentra el usuario, no se elimina
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
}
