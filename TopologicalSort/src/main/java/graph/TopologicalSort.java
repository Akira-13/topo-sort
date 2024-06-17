package graph;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort {

    private int i = 1;
    private int j = 1;

    public List<Vertex> topologicalSort(DirectedGraph graph) {
        ArrayList<Vertex> sortedVertices = new ArrayList<>();
        int size = graph.getAllVertices().size();
        for (int r = 1; r <= size; r++) {
            Vertex v = graph.getMinimalVertex();
            if (v == null) {
                throw new IllegalArgumentException("Grafo con ciclo");
            }
            v.setNum(r);
            graph.removeVertex(v.getId());
            sortedVertices.add(v);
            //System.out.println(graph.toString());
        }
        return sortedVertices;
    }

    public void topologicalSorting(DirectedGraph graph) {
        while (graph.vertexWithNumZero() != null) {
            TS(graph.vertexWithNumZero());
        }
        System.out.println(graph.topologicalOrdering());
        i = j = 1;
    }

    private void TS(Vertex v) {
        v.setNum(i++);
        for(Vertex adjacentVertex : (List<Vertex>)  v.getEdges()){
            if(adjacentVertex.getNum() == 0){
                TS(adjacentVertex);
            }
            else if(adjacentVertex.getTSnum() == 0){
                throw new IllegalArgumentException("Grafo con ciclo");
            }
        }
        v.setTSnum(j++);
    }
}
