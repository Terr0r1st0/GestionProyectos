package com.gestionproyectos.app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestionproyectos.app.variables.Estudiante;

import jakarta.servlet.http.HttpSession;

import com.gestionproyectos.app.repositorio.EstudianteRepositorio;

@Controller
public class EstudianteControlador {
	
	@Autowired
	private EstudianteRepositorio estudianteRepositorio;

	@GetMapping("/loginEstudiante")
	public String mostrarLogin() {
		return "loginEstudiante";
	}
	
	@PostMapping("/loginEstudiante")
	public String loginEstudiante(@RequestParam String usuario, @RequestParam String contrasena, Model model,HttpSession session) {
		Estudiante estudiante = estudianteRepositorio.findByUsuarioAndContrasena(usuario, contrasena);
		if(estudiante != null) {
			session.setAttribute("usuario", usuario);
			session.setAttribute("estudiante", estudiante);
	        session.removeAttribute("proyectoSeleccionado");
			return "redirect:/inicioEstudiante";
		}else {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginEstudiante";
		}
	}
	
	@GetMapping("/inicioEstudiante")
	public String inicioEstudiante(HttpSession session, Model model) {
		String estudiante = (String) session.getAttribute("usuario");
		model.addAttribute("estudiante",estudiante);
		return "indexEstudiante";
	}
}
