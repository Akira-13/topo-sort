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
 * La clase PlanificadorCursos permite la gestión de un plan de estudios universitario,
 * representando cursos y sus relaciones de prerrequisitos mediante un grafo dirigido.
 * Proporciona funcionalidades para agregar cursos, determinar el orden de toma de cursos,
 * y obtener cursos disponibles tras completar ciertos cursos.
 * 
 * @autor camila
 */

public class PlanificadorCursos {
    private DirectedGraph<String> mallaCurricular;
    LinkedListTDA<Curso> todoCurso = new LinkedListTDA<>();
    
    /**
     * Constructor de la clase PlanificadorCursos. Inicializa un grafo dirigido vacío
     * para representar la malla curricular.
     */
    public PlanificadorCursos() {
        mallaCurricular = new DirectedGraph<>();
    }
    

    /**
     * Método para obtener el grafo dirigido que representa la malla curricular.
     * 
     * @return El grafo dirigido que representa la malla curricular.
     */
    public DirectedGraph<String> getMallaCurricular() {
        return mallaCurricular;
    }
    
    
    
    /**
     * Método para agregar un curso y sus cursos abiertos (cursos que pueden tomarse
     * después de completar el curso actual).
     * 
     */
    public void agregarCurso(Curso curso, LinkedListTDA<String> cursosAbiertos) {
        todoCurso.add(curso);
        mallaCurricular.addVertex(curso.getId());
        if (cursosAbiertos != null && !cursosAbiertos.isEmpty()){
            agregarCursosAbiertos(curso, cursosAbiertos);
        }
    }
    
    /**
     * Método auxiliar para agregar los cursos abiertos por un curso.
     * 
     * @param curso El curso que abre otros cursos.
     * @param cursosAbiertos Lista de identificadores de los cursos abiertos por el curso actual.
     */
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
    
    /* Método para obtener un curso a partir de su identificador.
     * 
     * @param cursoId El identificador del curso.
     * @return El curso correspondiente al identificador, o null si no se encuentra.
     */
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
    
    /**
     * Método para marcar un curso como completado.
     * 
     * @param cursoId El identificador del curso a marcar como completado.
     */
     public void cursoCompletado(String cursoId) {
        getCurso(cursoId).setCompletado(true);
    }
    
    /**
     * Método para obtener el orden topológico de los cursos en la malla curricular.
     * 
     * @return Una lista con los identificadores de los cursos en orden topológico.
     * @throws Exception Si hay un ciclo en la malla curricular.
     */
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
    
    /**
     * Método para obtener los prerrequisitos de un curso.
     * 
     * @param cursoId El identificador del curso.
     * @return Una lista de identificadores de los cursos prerrequisitos.
     */
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
    
    /**
     * Método para obtener los cursos que no tienen prerrequisitos.
     * 
     * Se verifica si la lista obtenerPrerrequisitos es vacia
     * @return Una lista de identificadores de los cursos sin prerrequisitos.
     */
     public LinkedListTDA<String> obtenerCursosSinPrerrequisitos() {
        LinkedListTDA<String> cursosSinPrerrequisitos = new LinkedListTDA<>();
        for (Vertex<String> curso : mallaCurricular.getAllVertices()) {
            if (obtenerPrerrequisitos(curso.getId()).isEmpty()) {
                cursosSinPrerrequisitos.add(curso.getId());
            }
        }
        return cursosSinPrerrequisitos;
    }
    
     /**
     * Método para obtener los cursos abiertos (cursos que pueden tomarse 
     * después de completar un curso específico).
     * 
     * @param cursoId El identificador del curso.
     * @return Una lista de identificadores de los cursos abiertos.
     */
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
