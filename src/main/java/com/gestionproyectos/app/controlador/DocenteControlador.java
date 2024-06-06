package com.gestionproyectos.app.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.gestionproyectos.app.variables.Docente;
import jakarta.servlet.http.HttpSession;
import com.gestionproyectos.app.repositorio.DocenteRepositorio;

@Controller
public class DocenteControlador {

	@Autowired
	private DocenteRepositorio docenteRepositorio;
	
	@GetMapping("/loginDocente")
	public String mostrarLoginDocente() {
		return "loginDocente";
	}
	
	@PostMapping("/loginDocente")
	public String loginDocente(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        Docente docente = docenteRepositorio.findByUsuarioAndContrasena(usuario, contrasena);
        if (docente != null) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("cedulaDocente", docente.getCedulaDocente());
            session.setAttribute("rol", docente.getRol());
            
            if (docente.getRol() == Docente.Rol.Director) {
                return "redirect:/inicioDirector";
            } else if (docente.getRol() == Docente.Rol.Evaluador) {
                return "redirect:/inicioEvaluador";
            }
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "loginDocente";
    }
	
	@GetMapping("/inicioDirector")
	public String inicioDirector(HttpSession session, Model model) {
		String director = (String) session.getAttribute("usuario");
		model.addAttribute("director",director);
		return "indexDirector";
	}
	
	@GetMapping("/inicioEvaluador")
	public String inicioEvaluador(HttpSession session, Model model) {
		String evaluador = (String) session.getAttribute("usuario");
		model.addAttribute("evaluador",evaluador);
		return "indexEvaluador";
	}
	
}
