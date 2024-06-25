package uni.aed.linkedlistTDA;

import uni.aed.listTDA.NoSuchElementException;
import uni.aed.listTDA.IteratorTDA;

public class Iterador<E> implements IteratorTDA<E> {
    private Nodo actual;
    public Iterador(Nodo nodo){
        this.actual=nodo;
    }
    @Override
    public boolean hasNext() {
        return actual!=null;
    }

    @Override
    public E next() throws NoSuchElementException {
        if (actual==null)
                throw new NoSuchElementException("No más elementos");
        Object data=actual.getData();
        actual=actual.getNext();
        return (E)data;
    }
    
    
}