package com.gestionproyectos.app.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionproyectos.app.variables.Estudiante;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {
	Estudiante findByUsuarioAndContrasena(String usuario, String contrasena);
	Estudiante findByCedulaEstudiante(Integer cedulaEstudiante);
	Estudiante findByUsuario(String usuario);
}
