import java.io.*;
import java.util.LinkedList;

/**
 * Implements some methods to determine how customer information should be handled and displayed.
 */
public class Customer implements Comparable<Customer> {
    private final String fName, lName;
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
    
    public SortedLinkedList<Pair<Tickets, Integer>> getTicketsHeld() {
        return ticketsHeld;
    }
    
    public Pair<Tickets, Integer> getTicketHeld(Tickets ticket) {
        for (Pair<Tickets, Integer> pair : ticketsHeld) {
            if (pair.getFirst() == ticket) {
                return pair;
            }
        }
        return null;
    }
    
    public void addNewTicket(Tickets t, int quantity) { //3 ticket restriction imposed upon method call
            this.ticketsHeld.add(new Pair<>(t, quantity));
    }
    
    public void removeTickets(Pair<Tickets, Integer> t) {
       ticketsHeld.remove(t);
    }
    
    public void alterTicketCount(Pair<Tickets, Integer> t, int quantity) {
        t.setSecond(t.getSecond() + quantity);
    }
    
    public int compareTo(Customer c) {
        int lNameComparison = this.lName.compareTo(c.getLName());
        if (lNameComparison != 0) {
            return lNameComparison;
        }
        else
            return this.fName.compareTo(c.getFName());
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return c.lName.equals(this.lName) && c.fName.equals(this.fName);
        }
        return false;
    }
    
    /** This method returns a string output
     * for the tickets to be reproduced when customer data is queried, the other getter methods wouldn't provide the
     * correct formatting.
     */
    public String printTicketsHeld(){
        StringBuilder sb = new StringBuilder();
        for (Pair<Tickets, Integer> pair : ticketsHeld) {
            sb.append(pair.getSecond()).append(" ").append(pair.getFirst().getLineName()).append(" tickets\n");
        }
        if (sb.isEmpty()) {
            return "No tickets\n";
        }
        return sb.toString();
    }
    
    public String toString() {
        return String.format("%s %s holds: \n%s", fName, lName, printTicketsHeld());
        
    }
    
    /** This method is for calculating the total cost and total discount from each type of ticket in a customers account.
     *  Discount type is calculated based on the quantities of tickets specified in the coursework instructions
     *  A new letter is written to letters.txt for every non-eligible ticket in the customers account
    */
    public String ticketTotal() {
        double total = 0.0;
        double discount = 0.0;
        double discountType= 0.0;
        int numberOfTickets = 0;
        for (Pair<Tickets, Integer> pair : ticketsHeld) {
            numberOfTickets += pair.getSecond();
            total += pair.getFirst().getTicketPrice()*pair.getSecond(); // multiply ticket price by quantity
        }
        if (numberOfTickets >=26)
            discountType = Tickets.getD3();
        else if (numberOfTickets >=11)
            discountType = Tickets.getD2();
        else if (numberOfTickets >=6)
            discountType = Tickets.getD1();
        else {
            PrintWriter writer;
            try {
                writer = new PrintWriter(new FileOutputStream("src/letters", true));
               }
            catch (FileNotFoundException e) {
                return "There was an error writing to letters.txt";
            }
                writer.print(letter(numberOfTickets));
                writer.flush();
            }
        discount += discountType * total;

        if (discount != 0) { //Makes sure savings isn't displayed if there aren't any
            return String.format("Cost: £%.2f\nSavings: £%.2f\n\n", total-discount, discount);
        }
        else {
            return String.format("Cost: £%.2f\n\n", total);
        }
    }
    
    /** This method is just for formatting the letter output. It also calculates how many tickets are needed before any
     * discount will apply.
     */
    public String letter(int tickets) {
        int reqTickets = 6 - tickets;
        return "Dear " + fName + " " + lName + ",\n\n" +
                "You do not currently qualify for a discount on your tickets.\n" +
                "You need to purchase " + reqTickets + " more to qualify.\n\n" ;
    }
}
