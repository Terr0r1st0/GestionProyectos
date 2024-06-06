package com.gestionproyectos.app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.gestionproyectos.app.variables.Coordinador;

import jakarta.servlet.http.HttpSession;

import com.gestionproyectos.app.repositorio.CoordinadorRepositorio;

@Controller
public class CoordinadorControlador {

	@Autowired
	private CoordinadorRepositorio coordinadorRepositorio;
	
	@GetMapping("/loginCoordinador")
	public String mostrarLogin() {
		return "loginCoordinador";
	}
	
	@PostMapping("/loginCoordinador/ingreso")	
    public String login(@RequestParam String usuario, @RequestParam String contrasena, Model model,HttpSession session) {
    	Coordinador coordinador = coordinadorRepositorio.findByUsuarioAndContrasena(usuario, contrasena);
        if (coordinador != null) {
        	session.setAttribute("usuario",usuario);
            return "redirect:/inicioCoordinador";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginCoordinador";
        }
    }
	
	@GetMapping("/inicioCoordinador")
	public String inicioCoordinador(HttpSession session, Model model) {
		String coordinador = (String) session.getAttribute("usuario");
		model.addAttribute("usuarioLogeado",coordinador);
		return "indexCoordinador";
	}
	
}
