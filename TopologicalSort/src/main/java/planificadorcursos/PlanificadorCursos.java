/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificadorcursos;

import graph.DirectedGraph;
import graph.TopologicalSort;
import graph.Vertex;
import uni.aed.linkedlistTDA.*;
import uni.aed.listTDA.*;

/**
 *
 * @author camila
 */
public class PlanificadorCursos {
    private DirectedGraph<String> mallaCurricular;
    LinkedListTDA<Curso> todoCurso = new LinkedListTDA<>();
    public PlanificadorCursos() {
        mallaCurricular = new DirectedGraph<>();
    }

    public DirectedGraph<String> getMallaCurricular() {
        return mallaCurricular;
    }
    
    
    
    // Agregar curso y los cursos que abre
    public void agregarCurso(Curso curso, LinkedListTDA<String> cursosAbiertos) {
        todoCurso.add(curso);
        mallaCurricular.addVertex(curso.getId());
        if (cursosAbiertos != null && !cursosAbiertos.isEmpty()){
            agregarCursosAbiertos(curso, cursosAbiertos);
        }
    }
    
    public void agregarCursosAbiertos(Curso curso, LinkedListTDA<String> cursosAbiertos) {
        IteratorTDA<String> iterador = cursosAbiertos.iterator();
        while (iterador.hasNext()) {
        String cursoAbierto = iterador.next();
            Curso n = new Curso(cursoAbierto);
            todoCurso.add(n);
            mallaCurricular.addVertex(cursoAbierto);
            mallaCurricular.addEdge(curso.getId(), cursoAbierto);
        }
    }
    
    public Curso getCurso(String cursoId){
        IteratorTDA<Curso> iterador = todoCurso.iterator();
        while (iterador.hasNext()) {
            Curso curso = iterador.next();
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
    public LinkedListTDA<String> obtenerOrdenTopologico() throws Exception {
        TopologicalSort<String> topoSort = new TopologicalSort<>();
        topoSort.topologicalSortDFS(mallaCurricular);
        LinkedListTDA<String> resultado = new LinkedListTDA<>();
        
        IteratorTDA<Vertex<String>> iterador = mallaCurricular.getTopologicalOrdering().iterator();
        while (iterador.hasNext()) {
        Vertex<String> v = iterador.next();
            resultado.add(v.getId());
        }
        return resultado;
    }
    
    // Cursos disponibles después de completar varios cursos
    public LinkedListTDA<String> obtenerCursosDisponibles(LinkedListTDA<String> cursosCompletados) throws Exception {
        // Marcar los cursos completados
        IteratorTDA<String> iterador = cursosCompletados.iterator();
        while (iterador.hasNext()) {
        String cursoId = iterador.next();
             cursoCompletado(cursoId);
        }

        // Obtener el orden topológico
        TopologicalSort<String> topoSort = new TopologicalSort<>();
        topoSort.topologicalSortDFS(mallaCurricular);
        
        LinkedListTDA<String> cursosDisponibles = new LinkedListTDA<>();
        //Recorre 
        IteratorTDA<Vertex<String>> iterador1 = mallaCurricular.getTopologicalOrdering().iterator();
        while (iterador1.hasNext()) {
        Vertex<String> v = iterador1.next();
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
        LinkedListTDA<String> prerrequisitos = obtenerPrerrequisitos(cursoId);

        // Si el curso no tiene prerrequisitos, es apto
        if (prerrequisitos.isEmpty()) {
            return true;
        }

        // Verificar si todos los prerrequisitos están completados
        IteratorTDA<String> iterador = prerrequisitos.iterator();
        while (iterador.hasNext()) {
        String prerrequisitoId = iterador.next();
            Curso cursoPrerrequisito = getCurso(prerrequisitoId);
            if (!cursoPrerrequisito.isCompletado()) {
                return false;
            }
        }
        return true;  
    }
    
    public LinkedListTDA<String> obtenerPrerrequisitos(String cursoId) {
        LinkedListTDA<String> prerrequisitos = new LinkedListTDA<>();
    
        for (Vertex<String> vertex : mallaCurricular.getAllVertices()) {
            IteratorTDA<Vertex<String>> iterador = vertex.getEdges().iterator();
            while (iterador.hasNext()) {
            Vertex<String> edge = iterador.next();
                if (edge.getId().equals(cursoId)) {
                    prerrequisitos.add(vertex.getId());
                }
            }
        }
        return prerrequisitos;
    }
    
     public LinkedListTDA<String> obtenerCursosSinPrerrequisitos() {
        LinkedListTDA<String> cursosSinPrerrequisitos = new LinkedListTDA<>();
        for (Vertex<String> curso : mallaCurricular.getAllVertices()) {
            if (obtenerPrerrequisitos(curso.getId()).isEmpty()) {
                cursosSinPrerrequisitos.add(curso.getId());
            }
        }
        return cursosSinPrerrequisitos;
    }
    public LinkedListTDA<String> obtenerCursosAbiertos(String cursoId) {
        LinkedListTDA<String> cursosAbiertos = new LinkedListTDA<>();
        
        IteratorTDA<Vertex<String>> iterador = mallaCurricular.getVertex(cursoId).getEdges().iterator();
            while (iterador.hasNext()) {
            Vertex<String> curso = iterador.next();
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
