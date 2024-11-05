import java.io.*;
import java.util.LinkedList;

public class Customer implements Comparable<Customer> {
    private final String fName, lName;
    private final SortedLinkedList<Pair<Tickets, Integer>> ticketsHeld;
    
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
    
    public String ticketTotal() {
        double total = 0.0;
        double discountTotal = 0.0;
        for (Pair<Tickets, Integer> pair : ticketsHeld) {
           double ticketPrice = pair.getSecond() * pair.getFirst().getTicketPrice();
           if (pair.getSecond() >= 26) {
               discountTotal += ticketPrice - (ticketPrice / Tickets.getD3());
               ticketPrice = ticketPrice / Tickets.getD3(); //Applies 3rd discount
           }
           else if (pair.getSecond() >= 11) {
               discountTotal += ticketPrice - (ticketPrice / Tickets.getD2());
               ticketPrice = ticketPrice / Tickets.getD2(); //Applies 2nd discount
           }
           else if (pair.getSecond() >= 6) {
               discountTotal += ticketPrice - (ticketPrice / Tickets.getD1());
               ticketPrice = ticketPrice / Tickets.getD1();
           }
           else {
               PrintWriter writer;
               try {
                   writer = new PrintWriter(new FileOutputStream("src/letters"));
               } catch (FileNotFoundException e) {
                   return "There was an error writing to letters.txt";
               }
               writer.print(letter(pair));
               writer.close();
           }
           total += ticketPrice;
        }
        return "Total cost: " + String.format("£%.2f", total) + "\nSavings: " + String.format("£%.2f\n", discountTotal);
    }
    
    public String letter(Pair<Tickets, Integer> pair) {
        int reqTickets = 6 - pair.getSecond();
        return "Dear " + fName + " " + lName + ",\n\n" +
                "You do not currently qualify for a discount on your " + pair.getFirst().getLineName() + " tickets.\n" +
                "You need to purchase " + reqTickets + " more to qualify.\n\n" ;
    }
    
}