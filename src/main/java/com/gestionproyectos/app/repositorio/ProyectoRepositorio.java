package com.gestionproyectos.app.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionproyectos.app.variables.Docente;
import com.gestionproyectos.app.variables.Estudiante;
import com.gestionproyectos.app.variables.Proyecto;

public interface ProyectoRepositorio extends JpaRepository<Proyecto, Integer> {
	List<Proyecto> findByDirector(Docente director);
	List<Proyecto> findByEvaluador(Docente evaluador);
	List<Proyecto> findByEstudiante(Estudiante estudiante);
}
