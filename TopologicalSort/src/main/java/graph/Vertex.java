package graph;

import uni.aed.linkedlistTDA.LinkedListTDA;
import uni.aed.linkedlistTDA.Iterador;

public class Vertex<E> {

    private final E id;
    private final LinkedListTDA<Vertex<E>> adjacentVertices;
    private int num, TSnum;
    private int exitGrade;

    public Vertex(E id) {
        this.id = id;
        this.adjacentVertices = new LinkedListTDA<>();
        num = TSnum = exitGrade = 0;
    }

    public E getId() {
        return id;
    }

    public int getExitGrade() {
        return exitGrade;
    }

    public LinkedListTDA<Vertex<E>> getEdges() {
        return adjacentVertices;
    }

    public void addAdjacentVertex(Vertex<E> vertex) {
        adjacentVertices.add(vertex);
        exitGrade++;
    }

    public void removeAdjacentVertex(Vertex<E> vertex) {
        if (adjacentVertices.delete(vertex)) {
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
        Iterador<Vertex<E>> it = adjacentVertices.iterator();
        while (it.hasNext()) {
            string.append(it.next().getId()).append(", ");
        }
        if (!adjacentVertices.isEmpty()) {
            string.delete(string.length() - 2, string.length());
        }
        string.append("} TSNum: ").append(TSnum);
        //string.append(" num: ").append(num);
        return string.toString();
    }

}
