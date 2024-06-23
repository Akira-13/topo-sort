package graph;

import java.util.*;

public class TSMain {

    private static Scanner scr;

    public TSMain() {
        scr = new Scanner(System.in).useDelimiter("\n");
    }

    public static void main(String[] args) {
        TSMain main = new TSMain();
        main.showMenu();
    }

    private void showMenu() {
        int opcion = 1;
        String Rpta = "S";
        String SEPARADOR = "\n";
        try {
            do {
                System.out.print("Ordenador topologico de grafos" + SEPARADOR
                        + "1.- Generar grafo por defecto(Referencia: Drozdek)" + SEPARADOR
                        + "2.- Construir grafo " + SEPARADOR
                        + "3.- Salir " + SEPARADOR + "Elija una opcion: ");
                opcion = scr.nextInt();
                switch (opcion) {
                    case 1 -> {
                        generateDefaultGraph();
                    }
                    case 2 -> {
                        createGraph();
                    }
                    default -> {
                        break;
                    }
                }
                System.out.print("\nPara continuar con las operaciones pulsa S; Para finalizar pulse N: ");
                Rpta = scr.next().toUpperCase();
            } while (Rpta.equals("S") == true);
        } catch (InputMismatchException ex) {
            System.out.println("Debe ingresar obligatoriamente un número entero como opcion elegida.");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void generateDefaultGraph() {
        TopologicalSort<String> ts = new TopologicalSort<>();
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
        System.out.println("Grafo generado:");
        System.out.println(defaultGraph.toString());

        System.out.println("\nGrafo ordenado mediante DFS:");
        ts.topologicalSortDFS(defaultGraph);
        ArrayList<Vertex<String>> orderedVertices
                = (ArrayList<Vertex<String>>) defaultGraph.getTopologicalOrdering();
        for (Vertex<String> v : orderedVertices) {
            System.out.print(v.getId() + ", ");
        }
        
        defaultGraph.clearGraph();
        System.out.println("\nGrafo ordenado sin DFS:");
        orderedVertices
                = (ArrayList<Vertex<String>>) ts.topologicalSort(defaultGraph);
        for (Vertex<String> v : orderedVertices) {
            System.out.print(v.getId() + ", ");
        }
    }
    
    private void createGraph(){
        TopologicalSort<String> ts = new TopologicalSort<>();
        int n;
        String input;
        System.out.print("Ingrese el numero de vertices: ");
        n = scr.nextInt();
        if(n <= 0){
            System.out.print("Numero de vertices invalido: ");
            n = scr.nextInt();
        }
        scr.nextLine();
        ArrayList<String> vertices = new ArrayList<>(n);
        DirectedGraph<String> graph = new DirectedGraph<>();
        for(int i = 0; i < n; i++){
            System.out.print("Ingrese clave del vertice " + i + ": ");
            input = scr.nextLine();
            graph.addVertex(input);
            vertices.add(input);
        }
        for(int i = 0; i < n; i++){
            System.out.println("Ingrese los vertices conectados al vertice " + 
                    vertices.get(i) + " separados por espacios");
            input = scr.nextLine();
            String[] edges = input.split(" ");
            
            for (String edge : edges) {
                if(graph.getVertex(edge) == null){
                    continue;
                }
                graph.addEdge(vertices.get(i), edge);
            }
        }
        System.out.println(graph.toString());
        
        System.out.println("\nGrafo ordenado mediante DFS:");
        ts.topologicalSortDFS(graph);
        ArrayList<Vertex<String>> orderedVertices
                = (ArrayList<Vertex<String>>) graph.getTopologicalOrdering();
        for (Vertex<String> v : orderedVertices) {
            System.out.print(v.getId() + ", ");
        }
        
        graph.clearGraph();
        System.out.println("\nGrafo ordenado sin DFS:");
        orderedVertices
                = (ArrayList<Vertex<String>>) ts.topologicalSort(graph);
        for (Vertex<String> v : orderedVertices) {
            System.out.print(v.getId() + ", ");
        }
        
    }
}
