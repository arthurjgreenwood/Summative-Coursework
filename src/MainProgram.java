import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is for running the main bulk of the program.
 */

public class MainProgram {
    
    public static SortedLinkedList<Customer> validCustomers = new SortedLinkedList<>();
    public static SortedLinkedList<Tickets> availableTickets = new SortedLinkedList<>();
    
    public static void main(String[] args) {
        readIn();
        menu();
    }
    
    /**
     * Reads in the file and assigns the values to different SortedLinkedLists and the discounts to constants.
     * Only works for the specific type of formatting given in the input file, but I assume that is okay for this
     * assignment.
     */
    
    public static void readIn(){
        Scanner sc;
        try {
            sc = new Scanner(new File("src/input_data"));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }
            int Names = sc.nextInt();
            sc.nextLine();
            int i = 0;
            while (i < Names) {
                String fName = sc.next();
                String lName = sc.next();
                sc.nextLine();
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
                i++;
            }
            
            Tickets.setD1(sc.nextDouble());
            Tickets.setD2(sc.nextDouble());
            Tickets.setD3(sc.nextDouble());
            sc.close();
        
    }
    
    /**
     * The main loop of the program. I broke some of the longer switch expressions into their own methods as the code
     * was getting rather cluttered.
     */
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
                    for(Customer c: validCustomers) {
                        System.out.println(c);
                        if (!c.getTicketsHeld().isEmpty()) {
                            System.out.println(c.ticketTotal()); //ensures the ticket total is only run if the customers
                        }                                        //ticket list is empty. This allows for the "No tickets"
                    }                                            //clause to be printed instead.
                    break;
                case "a":
                    addTickets();
                    break;
                    
                case "r":
                    removeTickets();
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
    
    /**
     * Perhaps the most complex method in the program. This allows the user to pick a specific customer, and add tickets
     * to their account. This calls different methods depending on if a new ticket needs to be created or if an
     * existing quantity needs to be updated.
     */
    public static void addTickets(){
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
        
        Customer customer = null;
        for (Customer c : validCustomers) {
            if (c.compareTo(new Customer(fName, lName)) == 0) {
                customer = c;
                break;
            }
        }
        System.out.println("\n" + customer); //Shows the user which ticket types the customer has so they don't have to remember
        
        System.out.println();
        System.out.print("Select a ticket type: ");
        Tickets selection;
        try {
            selection = availableTickets.get(sc.nextInt() - 1);
        } catch (Exception e) {
            System.out.println("Invalid ticket type");
            return;
        }
        
        //IntelliJ insists that the possible NullPointerException from getTicketsHeld() is still a problem even after
        //the try catch block is added. I am not sure why, but it doesn't appear to cause any errors with the inputs
        //the way they are, and since they won't change I don't think it matters
        try {
            if (customer.getTicketsHeld().size() == 3 && !(customer.getTicketsHeld().contains(customer.getTicketHeld(selection)))){
                System.out.println("Customers cannot hold more than 3 kinds of ticket.");
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("There was an error acquiring customer data.");
            return;
        }
        
        
        System.out.print("Quantity: ");
        int quantity = sc.nextInt();
        
        try {
            if (quantity <= 0) //Prevents negative ticket adding
                System.out.println("Cannot add negative tickets.");
            else if (customer.getTicketsHeld().contains(customer.getTicketHeld(selection))) {
                    customer.alterTicketCount(customer.getTicketHeld(selection), quantity);
                    System.out.println("Successfully added " + quantity + " " + selection.getLineName() + " tickets to " + fName + " " + lName + "'s account!\n\n");
            }
            else {
                customer.addNewTicket(selection, quantity);
                System.out.println("Successfully added " + quantity + " " + selection.getLineName() + " tickets to " + fName + " " + lName + "'s account!\n\n");
            }
        } catch (NullPointerException e) {
            System.out.println("There was an error acquiring customer data.");
        } //I'm not sure if this exception will ever be met, but better to be safe
    }
    
    /**
     * I think this method is slightly better put together than the adding one, but works in a similar manner
     */
    public static void removeTickets() {
        Scanner sc = new Scanner(System.in);
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
        assert customer != null; //The validity of the customer is checked previously so we can make this assertation
        
        if (customer.getTicketsHeld().isEmpty()) {
            System.out.println(fName + lName + " has no tickets to remove");
            return;
        }
        System.out.println(customer + "\n");
        
        System.out.println("Removable tickets: ");
        int i = 1;
        for (Pair<Tickets, Integer> t : customer.getTicketsHeld()) {
            System.out.println(i + ". " + t.getFirst().getLineName());
            i++;
        }
        
        Pair<Tickets, Integer> ticketType;
        try {
            System.out.print("Select a ticket type to remove: ");
            ticketType = customer.getTicketsHeld().get(sc.nextInt()-1);
        } catch (Exception e) {
            System.out.println("Invalid ticket type");
            return;
        }
        System.out.print("Quantity: ");
        int quantity;
        
        try {
            quantity = sc.nextInt();
            
            if (quantity < 0) {
                System.out.println("Cannot remove negative tickets");
                return;
            } else if (quantity > ticketType.getSecond()) {
                System.out.println(fName + lName + " has fewer tickets than this");
                return;
            } else if (quantity == ticketType.getSecond()) {
                customer.removeTickets(ticketType);
            } else {
                customer.alterTicketCount(ticketType, -(quantity));
            }
            System.out.println("Successfully removed " + quantity + " " +
                    ticketType.getFirst().getLineName() + " tickets from " + fName + " " + lName + "'s account!\n\n");
        } catch (Exception e) {
                System.out.println("Invalid quantity");
        }
    }
}
