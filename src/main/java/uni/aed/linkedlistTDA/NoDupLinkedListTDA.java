//Codigo 20222510J
//Nombre Diego Akira Garcia Rojas
package uni.aed.linkedlistTDA;
//Subclase de LinkedListTDA
public class NoDupLinkedListTDA<E> extends LinkedListTDA<E>{
//Solo es necesario hacer Override a los métodos add
    @Override
    public void add(int index, E data) throws IndexOutOfBoundsException {
        //Para comparar adecuadamente Persona, se llama a su método compareTo
        //Si se usa solo el metodo contain() de LinkedList, 
        //no se puede comparar personas
        //Si es que ya se encuentra el dato ingresado (compareTo == 0), 
        // no se hace nada
        //Si es un dato unico, se llama al add() de la superclase
        for(int i = 0; i < this.size(); i++){
            if(((Comparable) this.get(i)).compareTo(data) == 0){
                return;
            }
        }
        super.add(index, data);
    }

    @Override
    public void add(E data) {
        //Se sigue el mismo procedimiento para este método
        //Ya que en la superclase tambien llama a add(index, data), se sigue la
        //misma logica
        for(int i = 0; i < this.size(); i++){
            if(((Comparable) this.get(i)).compareTo(data) == 0){
                return;
            }
        }
        super.add(data);
    }
}
