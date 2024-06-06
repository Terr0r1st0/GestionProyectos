package com.gestionproyectos.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionproyectos.app.variables.Docente;

public interface DocenteRepositorio extends JpaRepository<Docente, Integer> {
	Docente findByUsuarioAndContrasena(String usuario, String contrasena);
	List<Docente> findByRol(Docente.Rol rol);
	Docente findByUsuario(String usuario);
}
