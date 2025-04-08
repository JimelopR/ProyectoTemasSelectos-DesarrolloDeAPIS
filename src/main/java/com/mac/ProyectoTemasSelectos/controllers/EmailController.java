/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.controllers;

import com.mac.ProyectoTemasSelectos.models.EmailModel;
import com.mac.ProyectoTemasSelectos.models.UsuarioModel;
import com.mac.ProyectoTemasSelectos.repositories.TipoUsuarioRepository;
import com.mac.ProyectoTemasSelectos.repositories.UsuarioRepository;
import com.mac.ProyectoTemasSelectos.services.EmailService;
import com.mac.ProyectoTemasSelectos.services.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jimena
 */

@RestController
@RequestMapping
public class EmailController {
    

    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    EmailService emailService;  
    
    @PostMapping("/registrar-usuario")
    private ResponseEntity<String> registrarUsuario(@RequestBody UsuarioModel usuario){
       
        try {

             // Generamos una contraseña aleatoria
            String contrasenaGenerada = usuarioService.generarContrasenaAleatoria();
            usuario.setPassword(contrasenaGenerada); // Asignamos la contraseña generada al usuario
            
            // Guardamos el usuario con la nueva contraseña
            UsuarioModel nuevoUsuario = usuarioService.guardarUsuario(usuario);
            
            // Creamos el modelo de correo
            EmailModel email = new EmailModel();
            
            email.setUsuario(nuevoUsuario.getCorreo()); // El correo del usuario
            email.setAsunto("Bienvenido a nuestra plataforma"); // Asunto del correo
            email.setMensaje("Hola " + nuevoUsuario.getNombre() + ",\n\nTu cuenta ha sido registrada exitosamente.\nTu contraseña es: " + contrasenaGenerada);
            emailService.sendMail(email);
        
        } catch (MessagingException ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, "Error al enviar correo: ", ex);
            return new ResponseEntity<>("Error al enviar correo", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, "Error inesperado: ", ex);
            return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Correo enviado exitosamente", HttpStatus.OK);
    }
    
    
}
