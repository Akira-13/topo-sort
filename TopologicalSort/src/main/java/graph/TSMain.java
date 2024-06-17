package graph;

import java.util.ArrayList;

public class TSMain {

    public static void main(String[] args) {
        TSMain main = new TSMain();
        TopologicalSort ts = new TopologicalSort();
        DirectedGraph<String> graph = main.generateDefaultGraph();
        System.out.println(graph.toString());
        ts.topologicalSorting(graph);
        ArrayList<Vertex> vertices = (ArrayList<Vertex>) ts.topologicalSort(graph);
        for (Vertex<String> v : vertices) {
            System.out.println(v.toString());
        }
    }

    private DirectedGraph<String> generateDefaultGraph() {
        DirectedGraph<String> defaultGraph = new DirectedGraph<>();
        defaultGraph.addVertex("a");
        defaultGraph.addVertex("b");
        defaultGraph.addVertex("c");
        defaultGraph.addVertex("d");
        defaultGraph.addVertex("e");
        defaultGraph.addVertex("f");
        defaultGraph.addVertex("g");
        defaultGraph.addEdge("a", "c");
        defaultGraph.addEdge("a", "d");
        defaultGraph.addEdge("c", "d");
        defaultGraph.addEdge("d", "b");
        defaultGraph.addEdge("d", "e");
        defaultGraph.addEdge("b", "e");
        defaultGraph.addEdge("e", "g");
        defaultGraph.addEdge("c", "f");
        defaultGraph.addEdge("d", "f");
        return defaultGraph;
    }
}
