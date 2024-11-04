import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class Customer implements Comparable<Customer> {
    private String fName, lName;
    private SortedLinkedList<Pair<Tickets, Integer>> ticketsHeld;
    
    public Customer(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
        ticketsHeld = new SortedLinkedList<>();
    }
    
    public String getFName() {
        return fName;
    }
    
    public String getLName() {
        return lName;
    }
    
    public LinkedList<Pair<Tickets, Integer>> getTicketsHeld() {
        return ticketsHeld;
    }
    
    public void addTickets(Tickets t, int quantity) {
        if (ticketsHeld.size()<3) {
            this.ticketsHeld.add(new Pair<>(t, quantity));
        }
        
    }
    
    public void removeTickets(Pair<Tickets, Integer> t) {
       ticketsHeld.remove(t);
    }
    
    public void alterTickets(Pair<Tickets, Integer> t, int quantity) {
    
    }
    
    public int compareTo(Customer c) {
        if (c.lName.compareTo(this.lName) > 0) {
            return 1;
        } else if (c.lName.compareTo(this.lName) < 0) {
            return -1;
        } else if (c.lName.compareTo(this.lName) == 0) {
            if (this.fName.compareTo(c.fName) > 0) {
                return 1;
            } else if (this.fName.compareTo(c.fName) < 0) {
                return -1;
            }
        }
        return 0;
    }
    
    public boolean equals(Object obj) {
        Customer c = (Customer) obj;
        if (c.lName.equals(this.lName) && c.fName.equals(this.fName)) {
            return true;
        }
        return false;
    }
}