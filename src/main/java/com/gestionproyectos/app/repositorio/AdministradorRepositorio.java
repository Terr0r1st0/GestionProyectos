package com.gestionproyectos.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionproyectos.app.variables.Administrador;

public interface AdministradorRepositorio extends JpaRepository<Administrador, String> {
	Administrador findByUsuarioAndContrasena(String usuario, String contrasena);
}
