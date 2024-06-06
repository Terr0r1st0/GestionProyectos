package com.gestionproyectos.app.variables;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiante {
	
	@Id
    private Integer cedulaEstudiante;
	
    private String nombre;
	
    private String correo;
	
    private String telefono;
	
    private String usuario;
	
    private String contrasena;
    
	public Integer getCedulaEstudiante() {
		return cedulaEstudiante;
	}
	public void setCedulaEstudiante(Integer cedulaEstudiante) {
		this.cedulaEstudiante = cedulaEstudiante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
