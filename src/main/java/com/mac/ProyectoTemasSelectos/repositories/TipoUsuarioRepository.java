/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mac.ProyectoTemasSelectos.repositories;


import com.mac.ProyectoTemasSelectos.models.TipoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jimena
 */
@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioModel, Long> {

    // Si quieres buscar un tipo de usuario por su nombre (tipoUsuario), puedes agregar un método custom:
    TipoUsuarioModel findByNombreTipo(String nombreTipo);

  
}
