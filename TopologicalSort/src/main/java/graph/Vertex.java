package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex<E> {
    private final E id;
    private final List<Vertex<E>> adjacentVertices;
    private int num, TSnum;
    private int exitGrade;

    public Vertex(E id) {
        this.id = id;
        this.adjacentVertices = new ArrayList<>();
        num = TSnum = exitGrade = 0;
    }

    public E getId() {
        return id;
    }

    public int getExitGrade() {
        return exitGrade;
    }

    public List<Vertex<E>> getEdges() {
        return adjacentVertices;
    }

    public void addAdjacentVertex(Vertex<E> vertex){
        adjacentVertices.add(vertex);
        exitGrade++;
    }
    
    public void removeAdjacentVertex(Vertex<E> vertex){
        if(adjacentVertices.remove(vertex)){        
            exitGrade--;
        }
    }

    public int getNum() {
        return num;
    }

    public int getTSnum() {
        return TSnum;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTSnum(int TSnum) {
        this.TSnum = TSnum;
    }
    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(id).append(" -> {");
        for(Vertex v:adjacentVertices){
            string.append(v.getId()).append(", ");
        }
        if(!adjacentVertices.isEmpty()){
            string.delete(string.length()-2, string.length());
        }
        string.append("} Grado de salida: ").append(exitGrade);
        //string.append(" num: ").append(num);
        return string.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return id.equals(vertex.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
