package com.gestionproyectos.app.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestionproyectos.app.variables.*;
import com.gestionproyectos.app.repositorio.AdministradorRepositorio;
import com.gestionproyectos.app.repositorio.CoordinadorRepositorio;
import com.gestionproyectos.app.repositorio.DocenteRepositorio;
import com.gestionproyectos.app.repositorio.EstudianteRepositorio;

@Controller
public class AdministradorControlador {

    @Autowired
    private AdministradorRepositorio administradorRepositorio;
    
    @Autowired
    private CoordinadorRepositorio coordinadorRepositorio;
    
    @Autowired
    private DocenteRepositorio docenteRepositorio;
    
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    @GetMapping("/loginAdministrador")
    public String mostrarLoginAdministrador() {
        return "loginAdministrador";
    }
    
    @PostMapping("/login/ingreso")
    public String login(@RequestParam String usuario, @RequestParam String contrasena, Model model) {
    	Administrador administrador = administradorRepositorio.findByUsuarioAndContrasena(usuario, contrasena);
        if (administrador != null) {
            return "redirect:/mostrarUsuarios";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "loginAdministrador";
        }
    }
    
    @GetMapping("/mostrarUsuarios")
    public String mostrarUsuarios(Model model) {
    	List<Docente> listaDocentes = docenteRepositorio.findAll();
        model.addAttribute("docentes", listaDocentes);
        
        List<Estudiante> listaEstudiantes = estudianteRepositorio.findAll();
        model.addAttribute("estudiantes", listaEstudiantes);
        
        List<Coordinador> listaCoordinador = coordinadorRepositorio.findAll();
        model.addAttribute("coordinadores", listaCoordinador);
        
        return "mostrarAdmin";
    }
    
    @GetMapping("/mostrarUsuarios/agregarDocente")
    public String mostrarFormularioDocente (Model model) {
    	model.addAttribute("docente", new Docente());
    	return "crearDocente";
    }
    
    @GetMapping("/mostrarUsuarios/agregarEstudiante")
    public String mostrarFormularioEstudiante (Model model) {
    	model.addAttribute("estudiante", new Estudiante());
    	return "crearEstudiante";
    }
    
    @GetMapping("/mostrarUsuarios/agregarCoordinador")
    public String mostrarFormularioCoordinador (Model model) {
    	model.addAttribute("coordinador", new Coordinador());
    	return "crearCoordinador";
    }
    
    @PostMapping("/guardarDocente")
    public String guardarDocente(Docente docente) {
    	
    	if (docente.getCedulaDocente() != null && docenteRepositorio.existsById(docente.getCedulaDocente())) {
            docenteRepositorio.save(docente);
        }else {
        	docenteRepositorio.save(docente);
        }

        return "redirect:/mostrarUsuarios";
	}
    
    @PostMapping("/guardarEstudiante")
    public String guardarEstudiante(Estudiante estudiante) {
    	if(estudiante.getCedulaEstudiante() !=null && estudianteRepositorio.existsById(estudiante.getCedulaEstudiante())) {
    		estudianteRepositorio.save(estudiante);
    	}else {
    		estudianteRepositorio.save(estudiante);
    	}
    	return "redirect:/mostrarUsuarios";
    }
    
    @PostMapping("/guardarCoordinador")
    public String guardarCoordinador(Coordinador coordinador) {
    	if(coordinador.getCedulaCoordinador() !=null && coordinadorRepositorio.existsById(coordinador.getCedulaCoordinador())) {
    		coordinadorRepositorio.save(coordinador);
    	}else {
    		coordinadorRepositorio.save(coordinador);
    	}
    	
    	return "redirect:/mostrarUsuarios";
    }

    @GetMapping("/actualizarUsuario/{cedula}")
    public String mostrarFormularioActualizar(@PathVariable Integer cedula, Model model) {
        Docente docente = docenteRepositorio.findById(cedula).orElse(null);
        if (docente != null) {
            model.addAttribute("docente", docente);
            return "actualizarDocente";
        }
        Estudiante estudiante = estudianteRepositorio.findById(cedula).orElse(null);
        if (estudiante != null) {
            model.addAttribute("estudiante", estudiante);
            return "actualizarEstudiante";
        }
        Coordinador coordinador = coordinadorRepositorio.findById(cedula).orElse(null);
        if (coordinador != null) {
            model.addAttribute("coordinador", coordinador);
            return "actualizarCoordinador";
        }
        model.addAttribute("error", "Usuario no encontrado");
        return "redirect:/mostrarUsuarios";
    }

    @GetMapping("/eliminarUsuario/{cedula}")
    public String eliminarUsuario(@PathVariable Integer cedula) {
        docenteRepositorio.deleteById(cedula);
        estudianteRepositorio.deleteById(cedula);
        coordinadorRepositorio.deleteById(cedula);
        return "redirect:/mostrarUsuarios";
    }
}
