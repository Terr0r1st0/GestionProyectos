package com.gestionproyectos.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionproyectos.app.variables.Coordinador;

public interface CoordinadorRepositorio extends JpaRepository<Coordinador, Integer> {
	Coordinador findByUsuarioAndContrasena(String usuario, String contrasena);

}
