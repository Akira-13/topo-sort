package graph;

import java.util.*;

public class DirectedGraph<E> {

    private final Map<E, Vertex<E>> vertices;

    public DirectedGraph() {
        vertices = new HashMap<>();
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
            throw new IllegalArgumentException("Invalid vertex id");
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
        for (Vertex v : vertices.values()) {
            string.append(v.toString()).append("\n");
        }
        return string.toString();
    }

    public List<Vertex<E>> getTopologicalOrdering() {
        List<Vertex<E>> sortedVertices = new ArrayList<>(vertices.values());
        sortedVertices.sort(Comparator.comparingInt(Vertex::getTSnum));
        return sortedVertices;
    }
}
