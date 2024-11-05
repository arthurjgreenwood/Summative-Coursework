/**
 * This class is mostly boilerplate code for the ticket fields.
 */

public class Tickets implements Comparable<Tickets> {
    
    private String lineName;
    private double ticketPrice;
    private static double D1;
    private static double D2;
    private static double D3;
    
    public Tickets(String name, double price) {
        this.lineName = name;
        this.ticketPrice = price;
    }
    
    public static void setD1(double discount1) {
        D1 = discount1;
    }
    public static void setD2(double discount2) {
        D2 = discount2;
    }
    public static void setD3(double discount3) {
        D3 = discount3;
    }
    public static double getD1() {
        return D1;
    }
    public static double getD2() {
        return D2;
    }
    public static double getD3() {
        return D3;
    }
    public String getLineName(){
        return this.lineName;
    }
    public double getTicketPrice(){
        return this.ticketPrice;
    }
    
    
    @Override
    public int compareTo(Tickets tickets) {
            return this.lineName.compareTo(tickets.lineName);
    }
    
    public String toString() {
        return String.format("%-14s £%.2f", lineName, ticketPrice);
    }
}
