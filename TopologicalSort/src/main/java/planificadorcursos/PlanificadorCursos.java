/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificadorcursos;

import graph.DirectedGraph;
import graph.TopologicalSort;
import graph.Vertex;
import java.util.*;

/**
 *
 * @author camila
 */
public class PlanificadorCursos {
    private DirectedGraph<String> mallaCurricular;
    List<Curso> todoCurso = new ArrayList<>();
    public PlanificadorCursos() {
        mallaCurricular = new DirectedGraph<>();
    }

    public DirectedGraph<String> getMallaCurricular() {
        return mallaCurricular;
    }
    
    
    
    // Agregar curso y los cursos que abre
    public void agregarCurso(Curso curso, List<String> cursosAbiertos) {
        todoCurso.add(curso);
        mallaCurricular.addVertex(curso.getId());
        if (cursosAbiertos != null && !cursosAbiertos.isEmpty()){
            agregarCursosAbiertos(curso, cursosAbiertos);
        }
    }
    
    public void agregarCursosAbiertos(Curso curso, List<String> cursosAbiertos) {
        for (String cursoAbierto : cursosAbiertos) {
            Curso n = new Curso(cursoAbierto);
            todoCurso.add(n);
            mallaCurricular.addVertex(cursoAbierto);
            mallaCurricular.addEdge(curso.getId(), cursoAbierto);
        }
    }
    
    public Curso getCurso(String cursoId){
        for(Curso curso : todoCurso){
            if(curso.getId().equals(cursoId)){
                return curso;
            }
        } 
        return null;
    }
    
     public void cursoCompletado(String cursoId) {
        getCurso(cursoId).setCompletado(true);
    }
    
    // Ordenación topológica
    public List<String> obtenerOrdenTopologico() throws Exception {
        TopologicalSort<String> topoSort = new TopologicalSort<>();
        topoSort.topologicalSortDFS(mallaCurricular);
        List<String> resultado = new ArrayList<>();
        for (Vertex<String> v : mallaCurricular.getTopologicalOrdering()) {
            resultado.add(v.getId());
        }
        return resultado;
    }
    
    // Cursos disponibles después de completar varios cursos
    public List<String> obtenerCursosDisponibles(List<String> cursosCompletados) throws Exception {
        // Marcar los cursos completados
        for (String cursoId : cursosCompletados) {
            cursoCompletado(cursoId);
        }

        // Obtener el orden topológico
        TopologicalSort<String> topoSort = new TopologicalSort<>();
        topoSort.topologicalSortDFS(mallaCurricular);
        
        List<String> cursosDisponibles = new ArrayList<>();
        //Recorre 
        for (Vertex<String> v : mallaCurricular.getTopologicalOrdering()) {
            Curso curso = getCurso(v.getId());
            //Verifica si el curso no ha sido completado y si es apto 
            if (!curso.isCompletado() && cursoApto(curso.getId())) {
                cursosDisponibles.add(curso.getId());
            }
        }
        return cursosDisponibles;
    }

    private boolean cursoApto(String cursoId) {
        
        
        // Obtener los prerrequisitos del curso
        List<String> prerrequisitos = obtenerPrerrequisitos(cursoId);

        // Si el curso no tiene prerrequisitos, es apto
        if (prerrequisitos.isEmpty()) {
            return true;
        }

        // Verificar si todos los prerrequisitos están completados
        for (String prerrequisitoId : prerrequisitos) {
            Curso cursoPrerrequisito = getCurso(prerrequisitoId);
            if (!cursoPrerrequisito.isCompletado()) {
                return false;
            }
        }
        return true;  
    }
    
    public List<String> obtenerPrerrequisitos(String cursoId) {
    List<String> prerrequisitos = new ArrayList<>();
    for (Vertex<String> vertex : mallaCurricular.getAllVertices()) {
        for (Vertex<String> edge : vertex.getEdges()) {
            if (edge.getId().equals(cursoId)) {
                prerrequisitos.add(vertex.getId());
            }
        }
    }
    return prerrequisitos;
    }
    
     public List<String> obtenerCursosSinPrerrequisitos() {
        List<String> cursosSinPrerrequisitos = new ArrayList<>();
        for (Vertex<String> curso : mallaCurricular.getAllVertices()) {
            if (obtenerPrerrequisitos(curso.getId()).isEmpty()) {
                cursosSinPrerrequisitos.add(curso.getId());
            }
        }
        return cursosSinPrerrequisitos;
    }
    public List<String> obtenerCursosAbiertos(String cursoId) {
        List<String> cursosAbiertos = new ArrayList<>();
        for (Vertex<String> curso : mallaCurricular.getVertex(cursoId).getEdges()) {
            cursosAbiertos.add(curso.getId());
        }
        return cursosAbiertos;
    }
    //Ruta a un curso específico
    
    @Override
    public String toString() {
        return mallaCurricular.toString();
    }
}
