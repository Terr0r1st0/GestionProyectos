package com.gestionproyectos.app.variables;

import jakarta.persistence.*;

@Entity
@Table(name = "docente")
public class Docente {
	
	@Id
    private Integer cedulaDocente;
	
    private String nombre;
    
    private String correo;
	
    private String telefono;
	
    @Enumerated(EnumType.STRING)
    private Rol rol;
	
    private String usuario;
	
    private String contrasena;

    public enum Rol {
        Director,
        Evaluador
    }

	public Integer getCedulaDocente() {
		return cedulaDocente;
	}

	public void setCedulaDocente(Integer cedulaDocente) {
		this.cedulaDocente = cedulaDocente;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
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
