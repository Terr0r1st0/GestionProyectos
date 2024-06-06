package com.gestionproyectos.app.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestionproyectos.app.variables.Docente;
import com.gestionproyectos.app.variables.Docente.Rol;
import com.gestionproyectos.app.variables.Estudiante;
import com.gestionproyectos.app.variables.Proyecto;

import jakarta.servlet.http.HttpSession;

import com.gestionproyectos.app.repositorio.DocenteRepositorio;
import com.gestionproyectos.app.repositorio.EstudianteRepositorio;
import com.gestionproyectos.app.repositorio.ProyectoRepositorio;

@Controller
public class ProyectoControlador {
	
    @Autowired
    private DocenteRepositorio docenteRepositorio;
    
    @Autowired
    private ProyectoRepositorio proyectoRepositorio;
    
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;
    
    @GetMapping("/mostrarIdeaProyecto/agregarProyecto")
    public String mostrarFormularioCrearIdeaProyecto(Model model) {
        List<Docente> directores = docenteRepositorio.findByRol(Rol.Director);
        List<Docente> evaluadores = docenteRepositorio.findByRol(Rol.Evaluador);
        model.addAttribute("directores", directores);
        model.addAttribute("evaluadores", evaluadores);
        return "crearIdeaProyecto";
    }
    
    @PostMapping("/crearIdeaProyecto")
    public String crearProyecto(@RequestParam String titulo, @RequestParam Integer cedula_director, @RequestParam Integer cedula_evaluador, Model model) {
        Proyecto proyecto = new Proyecto();
        proyecto.setTitulo(titulo);

        Docente director = docenteRepositorio.findById(cedula_director).orElse(null);
        Docente evaluador = docenteRepositorio.findById(cedula_evaluador).orElse(null);

        if (director != null && evaluador != null) {
            proyecto.setDirector(director);
            proyecto.setEvaluador(evaluador);
            proyectoRepositorio.save(proyecto);
            return "redirect:/mostrarIdeaProyecto";
        } else {
            model.addAttribute("error", "Director o Evaluador no encontrado.");
            return "crearIdeaProyecto";
        }
    }
    
    @GetMapping("/mostrarIdeaProyecto")
    public String mostrarIdeaProyecto(Model model) {
        List<Proyecto> proyectos = proyectoRepositorio.findAll();
        model.addAttribute("proyectos", proyectos);
        return "mostrarIdeaProyecto";
    }
    
    @GetMapping("/estadoProyectoCoordinador")
    public String mostrarEstadoProyectos(Model model) {
        List<Proyecto> proyectos = proyectoRepositorio.findAll();
        List<Docente> docentes = docenteRepositorio.findAll();
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("docentes", docentes);
        model.addAttribute("estudiantes", estudiantes);

        return "estadoProyectoCoordinador";
    }
    
    @GetMapping("/editarIdeaProyecto/{idProyecto}")
    public String mostrarFormularioEditarProyecto(@PathVariable Integer idProyecto, Model model) {
        Proyecto proyecto = proyectoRepositorio.findById(idProyecto).orElseGet(null);
        if(proyecto != null) {
        	model.addAttribute("proyecto",proyecto);
        	return "actualizarIdeaProyecto";
        }
    	model.addAttribute("error","Usuario no encontrado");
    	return "redirect:/mostrarIdeaProyecto";
    }

    @PostMapping("/actualizarIdeaProyecto")
    public String actualizarIdeaProyecto(@RequestParam Integer idProyecto, @RequestParam String titulo) {
        Optional<Proyecto> proyectoOptional = proyectoRepositorio.findById(idProyecto);
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();
            proyecto.setTitulo(titulo);
            proyectoRepositorio.save(proyecto);
        }
        return "redirect:/mostrarIdeaProyecto";
    }

    @GetMapping("/eliminarIdeaProyecto/{id}")
    public String eliminarProyecto(@PathVariable("id") Integer id) {
        proyectoRepositorio.deleteById(id);
        return "redirect:/mostrarIdeaProyecto";
    }
    
    
    @GetMapping("/calificarDirectorProyecto")
    public String calificarDirectorProyecto(HttpSession session, Model model) {
        String usuario = (String) session.getAttribute("usuario");
        Docente director = docenteRepositorio.findByUsuario(usuario);
        List<Proyecto> proyectos = proyectoRepositorio.findByDirector(director);
        model.addAttribute("proyectos", proyectos);
        return "calificarDirectorProyecto";
    }
    
    @GetMapping("/calificarEvaluadorProyecto")
    public String calificarEvaluadorProyecto(HttpSession session, Model model) {
        String usuario = (String) session.getAttribute("usuario");
        Docente evaluador = docenteRepositorio.findByUsuario(usuario);
        List<Proyecto> proyectos = proyectoRepositorio.findByEvaluador(evaluador);
        model.addAttribute("proyectos", proyectos);
        return "calificarEvaluadorProyecto";
    }
    
    @PostMapping("/actualizarEstadoProyecto")
    public String actualizarEstadoProyecto(@RequestParam Integer idproyecto, @RequestParam String accion, @RequestParam String tipoEstado) {
        Optional<Proyecto> optionalProyecto = proyectoRepositorio.findById(idproyecto);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            switch (accion) {
                case "aprobar":
                    if ("director".equals(tipoEstado)) {
                        proyecto.setEstadoDirector(Proyecto.Estado.Aprobado);
                    } else if ("evaluador".equals(tipoEstado)) {
                        proyecto.setEstadoEvaluador(Proyecto.Estado.Aprobado);
                    }
                    break;
                case "rechazar":
                    if ("director".equals(tipoEstado)) {
                        proyecto.setEstadoDirector(Proyecto.Estado.Rechazado);
                    } else if ("evaluador".equals(tipoEstado)) {
                        proyecto.setEstadoEvaluador(Proyecto.Estado.Rechazado);
                    }
                    break;
                case "eliminar":
                    if ("director".equals(tipoEstado)) {
                        proyecto.setEstadoDirector(null);
                    } else if ("evaluador".equals(tipoEstado)) {
                        proyecto.setEstadoEvaluador(null);
                    }
                    break;
                default:
                    break;
            }
            proyectoRepositorio.save(proyecto);
        }
        if ("director".equals(tipoEstado)) {
            return "redirect:/calificarDirectorProyecto";
        } else if ("evaluador".equals(tipoEstado)) {
            return "redirect:/calificarEvaluadorProyecto";
        } else {
            return "redirect:/";
        }
    }
    
    @GetMapping("/seleccionarIdeaProyecto")
    public String mostrarSeleccionarIdeaProyecto(HttpSession session, Model model) {
        Estudiante estudiante = (Estudiante) session.getAttribute("estudiante");
        Proyecto proyectoSeleccionado = (Proyecto) session.getAttribute("proyectoSeleccionado");

        if (proyectoSeleccionado != null) {
            model.addAttribute("proyectos", List.of(proyectoSeleccionado));
        } else {
            List<Proyecto> proyectosDisponibles = proyectoRepositorio.findByEstudiante(null);
            model.addAttribute("proyectos", proyectosDisponibles);
        }

        return "seleccionarIdeaProyecto";
    }
    
    @PostMapping("/asignarEstudiante")
    public String asignarEstudiante(@RequestParam Integer idProyecto, HttpSession session) {
        String usuario = (String) session.getAttribute("usuario");
        Estudiante estudiante = estudianteRepositorio.findByUsuario(usuario);
        Optional<Proyecto> proyectoOptional = proyectoRepositorio.findById(idProyecto);
        
        if (proyectoOptional.isPresent() && estudiante != null) {
            Proyecto proyecto = proyectoOptional.get();
            proyecto.setEstudiante(estudiante);
            proyectoRepositorio.save(proyecto);
            session.setAttribute("proyectoSeleccionado", proyecto);
        }
        
        return "redirect:/seleccionarIdeaProyecto";
    }
    
    @PostMapping("/agregarUrl")
    public String agregarUrl(@RequestParam Integer idProyecto, @RequestParam String url,HttpSession session) {
        Optional<Proyecto> proyectoOptional = proyectoRepositorio.findById(idProyecto);
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();
            proyecto.setUrl(url);
            proyectoRepositorio.save(proyecto);
            session.setAttribute("proyectoSeleccionado", proyecto);
        }
        return "redirect:/seleccionarIdeaProyecto";
    }

    @PostMapping("/editarUrl")
    public String editarUrl(@RequestParam Integer idProyecto, @RequestParam String url,HttpSession session) {
        Optional<Proyecto> proyectoOptional = proyectoRepositorio.findById(idProyecto);
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();
            proyecto.setUrl(url);
            proyectoRepositorio.save(proyecto);
            session.setAttribute("proyectoSeleccionado", proyecto);
        }
        return "redirect:/seleccionarIdeaProyecto";
    }

    @PostMapping("/eliminarAsignacion")
    public String eliminarAsignacion(@RequestParam Integer idProyecto, HttpSession session) {
        Optional<Proyecto> proyectoOptional = proyectoRepositorio.findById(idProyecto);
        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();
            proyecto.setEstudiante(null);
            proyecto.setUrl(null);
            proyectoRepositorio.save(proyecto);
        }
        session.removeAttribute("proyectoSeleccionado");
        return "redirect:/seleccionarIdeaProyecto";
    }
    
    @GetMapping("/estadoProyectoEstudiante")
    public String mostrarEstadoProyectoEstudiante(HttpSession session, Model model) {
        Estudiante estudiante = (Estudiante) session.getAttribute("estudiante");

        if (estudiante != null) {
            List<Proyecto> proyectos = proyectoRepositorio.findByEstudiante(estudiante);
            model.addAttribute("proyectos", proyectos);
        } else {
            model.addAttribute("proyectos", List.of());
        }

        return "estadoProyectoEstudiante";
    }
}
