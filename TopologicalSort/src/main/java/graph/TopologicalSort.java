package graph;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort<E> {

    private int i;
    private int j;
    
    /*
    //  topologicalSort sin DFS   //
    Drozdek no especifica tipo de retorno.
    La función retorna una lista con los vértices ordenados
    empezando desde el primer vértice mínimo que se encuentre.
    */
    public List<Vertex<E>> topologicalSort(DirectedGraph<E> graph) {
        ArrayList<Vertex<E>> sortedVertices = new ArrayList<>();
        int size = graph.getAllVertices().size();
        for (int r = 1; r <= size; r++) {
            Vertex<E> v = graph.getMinimalVertex();
            if (v == null) {
                throw new IllegalArgumentException("Grafo con ciclo");
            }
            /*
            Actualiza ambos TSnum y num de Vertex
            */
            v.setTSnum(r);
            v.setNum(r);
            graph.removeVertex((E) v.getId());
            sortedVertices.add(v);
            //System.out.println(graph.toString());
        }
        return sortedVertices;
    }
    
    /*
    //  topologicalSort con DFS   //
    Drozdek sugiere imprimir los vertices después del ordenamiento.
    Se delega esta funcion a un metodo en la clase DirectedGraph para
    evitar que el algoritmo haga más de lo esperado.
    Se asigna un numero de orden a los vertices.
    */
    private void TS(Vertex<E> v) {
        v.setNum(i++);
        for(Vertex<E> adjacentVertex : v.getEdges()){
            if(adjacentVertex.getNum() == 0){
                TS(adjacentVertex);
            }
            else if(adjacentVertex.getTSnum() == 0){
                throw new IllegalArgumentException("Grafo con ciclo");
            }
        }
        v.setTSnum(j++);
    }
    
    public void topologicalSortDFS(DirectedGraph<E> graph) {
        i = j = 0;
        while (graph.vertexWithNumZero() != null) {
            TS(graph.vertexWithNumZero());
        }
        i = j = 0;
        /*
        List<Vertex<E>> orderedVertices = graph.getTopologicalOrdering();
        for(Vertex<E> v : orderedVertices){
            System.out.print(v.toString());
        }
        */
    }
}
