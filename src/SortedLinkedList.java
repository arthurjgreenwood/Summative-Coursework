import java.util.LinkedList;

/** This class is for implementing SortedLinkedList<E> where E is a comparable type
 *  It allows for values to be inserted into the list and for the list to remain ordered
 */
public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {
    
    public boolean add(E value){ //Make sure there is a null check when this method is called i.e. the list cannot be null
        
        if (value == null) return false;
        
        for (int i = 0; i < this.size(); i++){
            if (value.compareTo(this.get(i)) < 0){
                super.add(i, value);
                return true;
            }
        }
        super.add(value);
        return true;
    }
}