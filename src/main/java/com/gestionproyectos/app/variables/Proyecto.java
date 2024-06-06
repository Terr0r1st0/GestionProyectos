package com.gestionproyectos.app.variables;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto")
public class Proyecto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProyecto;
	
    private String titulo;
	
    @Enumerated(EnumType.STRING)
    private Estado estadoDirector;
    
    @Enumerated(EnumType.STRING)
    private Estado estadoEvaluador;
    
    private String url;

    @ManyToOne
    @JoinColumn(name = "cedula_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "cedula_director")
    private Docente director;

    @ManyToOne
    @JoinColumn(name = "cedula_evaluador")
    private Docente evaluador;

    public enum Estado {
        Aprobado,
        Rechazado
    }

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Estado getEstadoDirector() {
		return estadoDirector;
	}

	public void setEstadoDirector(Estado estadoDirector) {
		this.estadoDirector = estadoDirector;
	}

	public Estado getEstadoEvaluador() {
		return estadoEvaluador;
	}

	public void setEstadoEvaluador(Estado estadoEvaluador) {
		this.estadoEvaluador = estadoEvaluador;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Docente getDirector() {
		return director;
	}

	public void setDirector(Docente director) {
		this.director = director;
	}

	public Docente getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(Docente evaluador) {
		this.evaluador = evaluador;
	}

}
