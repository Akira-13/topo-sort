/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificadorcursos;

import graph.Vertex;

/**
 * La clase Curso representa un curso individual en el contexto de un planificador de cursos.
 * Extiende la clase Vertex<String>, lo que significa que cada curso es un vértice en un grafo dirigido,
 * donde el identificador del curso es un String. Además, la clase incluye un atributo booleano para 
 * indicar si el curso ha sido completado.
 * 
 * @autor camila
 */

public class Curso extends Vertex<String> {
    private boolean completado;
    
    
    public Curso(String id) {
        super(id);
        this.completado = false;
    }
    
    
    /**
     * Método para verificar si el curso ha sido completado.
     * 
     * @return true si el curso ha sido completado, false en caso contrario.
     */
    public boolean isCompletado() {
        return completado;
    }
    
    /**
     * Método para establecer el estado de completado del curso.
     * 
     * 
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
