package graph;

import java.util.*;
import uni.aed.linkedlistTDA.Iterador;
import uni.aed.linkedlistTDA.LinkedListTDA;

public class DirectedGraph<E> {
    
    private LinkedListTDA Lista;
    private final Map<E, Vertex<E>> vertices;

    public DirectedGraph() {
        vertices = new HashMap<>();
    }

    public DirectedGraph(E[] vertexIds, int[][] adjacencyMatrix) {
        this();
        for (E vertexId : vertexIds) {
            addVertex(vertexId);
        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Vertex<E> fromVertex = vertices.get(vertexIds[i]);
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 0) {
                    Vertex<E> toVertex = vertices.get(vertexIds[j]);
                    fromVertex.addAdjacentVertex(toVertex);
                }
            }
        }
    }

    public Vertex<E> getVertex(E id) {
        return vertices.get(id);
    }

    public void addVertex(E data) {
        vertices.putIfAbsent(data, new Vertex<>(data));
    }

    public void addEdge(E fromId, E toId) {
        Vertex<E> fromVertex = vertices.get(fromId);
        Vertex<E> toVertex = vertices.get(toId);
        if (fromVertex == null || toVertex == null) {
            throw new IllegalArgumentException("ID Inválido");
        }
        fromVertex.addAdjacentVertex(toVertex);
    }

    public Vertex<E> getMinimalVertex() {
        for (Vertex<E> v : vertices.values()) {
            if (v.getExitGrade() == 0) {
                return v;
            }
        }
        return null;
    }

    public Vertex<E> vertexWithNumZero() {
        for (Vertex<E> v : vertices.values()) {
            if (v.getNum() == 0) {
                return v;
            }
        }
        return null;
    }

    public void removeVertex(E data) {
        Vertex<E> remVertex = vertices.remove(data);
        for (Vertex<E> v : vertices.values()) {
            v.removeAdjacentVertex(remVertex);
        }
    }

    public Collection<Vertex<E>> getAllVertices() {
        return vertices.values();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Vertex<E> v : vertices.values()) {
            string.append(v.toString()).append("\n");
        }
        return string.toString();
    }

    public LinkedListTDA<Vertex<E>> getTopologicalOrdering() {
        LinkedListTDA<Vertex<E>> sortedV = new LinkedListTDA<>();
        List<Vertex<E>> sortedVertices = new ArrayList<>(vertices.values());
        if (sortedVertices.get(0).getTSnum() == 0) {
            throw new IllegalArgumentException("Grafo no ordenado");
        }
        sortedVertices.sort(Comparator.comparingInt(Vertex<E>::getTSnum));
        for(Vertex<E> v : sortedVertices){
            sortedV.add(v);
        }
        return sortedV;
    }

    /*
    Metodo para remover el TSNum y num de los vertices
    Esencialmente, lo desordena
    Solo para demostracion
     */
    public void clearGraph() {
        for (Vertex<E> v : vertices.values()) {
            v.setNum(0);
            v.setTSnum(0);
        }
    }
}
