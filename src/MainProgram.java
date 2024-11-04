import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainProgram {
    
    public static SortedLinkedList<Customer> validCustomers = new SortedLinkedList<>();
    public static SortedLinkedList<Tickets> availableTickets = new SortedLinkedList<>();
    
    public static void main(String[] args) {
        readIn();
        menu();
    }
    
    public static void readIn(){
        
        try {
            Scanner sc = new Scanner(new File("src/input_data"));
            
            int Names = sc.nextInt();
            sc.nextLine();
            int i = 0;
            while (i < Names) {
                String fName = sc.next();
                String lName = sc.next();
                sc.nextLine();
                System.out.println(fName + " " + lName);
                validCustomers.add(new Customer(fName, lName));
                i++;
            }
            
            i=0;
            int Routes = sc.nextInt();
            sc.nextLine();
            while (i < Routes) {
                String name = sc.nextLine();
                double price = sc.nextDouble();
                sc.nextLine();
                availableTickets.add(new Tickets(name, price));
                System.out.println(name + " " + price);
                i++;
            }
            
            Tickets.setD1(sc.nextDouble());
            Tickets.setD2(sc.nextDouble());
            Tickets.setD3(sc.nextDouble());
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    
    public static void menu(){
        while (true){
            System.out.println("""
                -------------------------------------------
                Please choose an option:
                t - display available tickets
                c - display customer info
                a - add tickets to customer profile
                r - remove tickets from customer profile
                f - finish
                """);
            Scanner sc = new Scanner(System.in);
            switch (sc.next()) {
                case "t":
                    availableTickets();
                    break;
                case "c":
                    System.out.println(validCustomers); //TODO write a method to print customer info
                    break;
                case "a":
                    addTickets();
                    break;
                    
                case "r":
                    System.out.print("Customer First Name: ");
                    String fName = sc.next();
                    System.out.print("Customer Last Name: ");
                    String lName = sc.next();
                    Customer customer = null;
                    for (Customer c : validCustomers) {
                        if (c.compareTo(new Customer(fName, lName)) == 0) {
                            customer = c;
                            break;
                        }
                    }
                    if (!validCustomers.contains(customer)) {
                        System.out.println("Customer not found");
                        return;
                    }
                    assert customer != null;
                    
                    if (customer.getTicketsHeld().isEmpty()) {
                        System.out.println(fName + lName + " has no tickets to remove");
                        return;
                    }
                    //TODO print specific customers holdings with numbers
                    try {
                        System.out.print("Select a ticket type to remove: ");
                        Pair<Tickets, Integer> ticketType = customer.getTicketsHeld().get(sc.nextInt()-1);
                        int quantity = sc.nextInt();
                        if (quantity > ticketType.getSecond()){
                            System.out.println(fName + lName + " does not have this many tickets");
                            return;
                        }
                        if (quantity == ticketType.getSecond()) {
                            customer.removeTickets(ticketType);
                        }
                        else {
                            int total = ticketType.getSecond() - quantity;
                            customer.
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid ticket type");
                    }
                    System.out.print("Quantity: ");
                    
                    
                    break;
                case "f":
                    return;
                default: System.out.println("Invalid option, please try again");
            }
        }
    }
    
    public static void availableTickets(){
        System.out.println();
        System.out.println("Available tickets:");
        for (int i = 0; i < availableTickets.size(); i++) {
            System.out.println(i+1 + ". " + availableTickets.get(i));
        }
    }
    
    public static void addTickets(){ //TODO: implement ability to add tickets to a type that already exist in the profile
        Scanner sc = new Scanner(System.in);
        System.out.print("Customer First Name: ");
        String fName = sc.next();
        System.out.print("Customer Last Name: ");
        String lName = sc.next();
        
        if (!validCustomers.contains(new Customer(fName, lName))) {
            System.out.println("Customer not found");
            return;
        }
        availableTickets();
        
        System.out.println();
        System.out.print("Select a ticket type: ");
        Tickets selection = null;
        try {
            selection = availableTickets.get(sc.nextInt() - 1);
        } catch (Exception e) {
            System.out.println("Invalid ticket type");
        }
        
        System.out.print("Quantity: ");
        int quantity = sc.nextInt();
        Customer customer = null;
        for (Customer c : validCustomers) {
            if (c.compareTo(new Customer(fName, lName)) == 0) {
                customer = c;
                break;
            }
        }
        assert customer != null; //Customer cannot equal null after the for loop, as validity is checked previously in this method
        customer.addTickets(selection, quantity);
    }
}
