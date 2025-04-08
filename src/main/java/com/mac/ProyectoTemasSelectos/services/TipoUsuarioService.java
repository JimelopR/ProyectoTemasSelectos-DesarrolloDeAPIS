/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.services;


import com.mac.ProyectoTemasSelectos.models.TipoUsuarioModel;
import com.mac.ProyectoTemasSelectos.repositories.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jimena
 */
@Service
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    // Método para obtener un TipoUsuario por su ID
    public TipoUsuarioModel obtenerTipoUsuarioPorId(Long id) {
        return tipoUsuarioRepository.findById(id).orElse(null);
    }

    // Método para obtener un TipoUsuario por su nombre
    public TipoUsuarioModel obtenerTipoUsuarioPorNombre(String tipoUsuario) {
        return tipoUsuarioRepository.findByNombreTipo(tipoUsuario);
    }
}