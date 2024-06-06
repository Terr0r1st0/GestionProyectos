package com.gestionproyectos.app.variables;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "coordinador")
public class Coordinador {
	
	@Id
    private Integer cedulaCoordinador;
	@NotEmpty
    private String nombre;
	@NotEmpty
    private String correo;
	@NotEmpty
    private String telefono;
	@NotEmpty
    private String usuario;
	@NotEmpty
    private String contrasena;
    
	public Integer getCedulaCoordinador() {
		return cedulaCoordinador;
	}
	public void setCedulaCoordinador(Integer cedulaCoordinador) {
		this.cedulaCoordinador = cedulaCoordinador;
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
