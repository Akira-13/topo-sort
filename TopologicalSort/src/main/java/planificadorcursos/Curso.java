/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificadorcursos;

import graph.Vertex;

/**
 *
 * @author camila
 */
public class Curso extends Vertex<String> {
    private boolean completado;
    
    
    public Curso(String id) {
        super(id);
        this.completado = false;
    }
    
    
    
    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
