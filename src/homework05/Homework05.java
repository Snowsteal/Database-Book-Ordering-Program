package homework05;

import hr.*;
import java.sql.Date;
import java.util.*;

public class Homework05 {

    static DataHandler dataHandler = new DataHandler();
    static Scanner scanner = new Scanner(System.in);
    
    static enum MenuReturn {
        None,
        MainMenu,
        MemberMenu,
        Quit,
        InvalidLogin
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        List<Book> books = dataHandler.SelectBooks();
//        System.out.printf("Returned %d results.\n", books.size());
//        for(Book b : books)
//        {
//            System.out.printf("Isbn: %s\nAuthor: %s\nTitle: %s\nPrice: %.2f\nSubject: %s\n\n", b.getIsbn(), b.getAuthor(), b.getTitle(), b.getPrice(), b.getSubject());
//            System.out.println("Press any key to continue.");
//            try {
//                //System.in.read();
//            }
//            catch(Exception e){
//    
//            }
//        }

        while(true) {
            MenuReturn mr = WelcomeMenu();
            switch(mr) {
                case Quit: return;
                case MainMenu: continue;
                default:
                    
            }
        }
        
    }
    
    static void PrintGap() {
        System.out.printf("\n\n\n\n\n");
    }
    
    static MenuReturn WelcomeMenu() {
        System.out.println("1. Member Login");
        System.out.println("2. New Member Registration");
        System.out.println("q. Quit");
        System.out.print("\nSelect you option: ");
        String s = scanner.nextLine();
        switch(s){
            case "1":   LoginMenu();
                break;
            case "2":   RegistrationMenu();
                break;
            case "q":   
                return MenuReturn.Quit;
            default:
        }
        
        return MenuReturn.None;
    }
    
    static MenuReturn LoginMenu() {
        String userid;
        String password;
        System.out.print("Enter userID: ");
        userid = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        
        Member member = dataHandler.SelectMember(userid, password);
        if(member == null) {
            System.out.print("Invalid login, press enter to return to main menu and try again");
            scanner.nextLine();
            return MenuReturn.MainMenu;
        }
        
        return MemberMenu(member);
    }

    static MenuReturn RegistrationMenu() {
        String fname;
        String lname;
        String address;
        String city;
        String state;
        int zip;
        String phone;
        String email;
        String userid;
        String password;
        String creditcardtype;
        String creditcardnumber;

        System.out.println("New Member Registration");
        System.out.print("Enter first name: ");
        fname = scanner.nextLine();
        System.out.print("Enter last name: ");
        lname = scanner.nextLine();
        System.out.print("Enter street address: ");
        address = scanner.nextLine();
        System.out.print("Enter city: ");
        city = scanner.nextLine();
        System.out.print("Enter state: ");
        state = scanner.nextLine();
        System.out.print("Enter zip: ");
        zip = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter phone: ");
        phone = scanner.nextLine();
        System.out.print("Enter email: ");
        email = scanner.nextLine();
        System.out.print("Enter userid: ");
        userid = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        System.out.print("Enter type of Credit Card(amex/visa): ");
        creditcardtype = scanner.nextLine();
        System.out.print("Enter Credit Card Number: ");
        creditcardnumber = scanner.nextLine();
      
        Member member = new Member(fname, lname, address, city, state, zip, phone, email, userid, password, creditcardtype, creditcardnumber);
        member.Print();
        if (dataHandler.RegisterMember(member)) {
            PrintGap();
            System.out.println("You have registered successfully.");
            member.Print();
            System.out.print("Press Enter to go back to Menu");
        } else {
            System.out.print("There was an error registering, press enter to return to main menu and try again");
        }

        scanner.nextLine();
        PrintGap();

        return MenuReturn.MainMenu;
    }

    static MenuReturn MemberMenu(Member member) {
        outerloop:
        while (true) {
            System.out.println("Member Menu");
            System.out.println("1. Browse by Subject");
            System.out.println("2. Search by Author/Title");
            System.out.println("3. View/Edit Shopping Cart");
            System.out.println("4. Check Order Status");
            System.out.println("5. Check Out");
            System.out.println("6. Cne Click Check Out");
            System.out.println("7. View/Edit Personal Information");
            System.out.println("8. Logout");
            System.out.print("\nSelect your option: ");

            String s = scanner.nextLine();
            MenuReturn mr = MenuReturn.None;
            switch (s) {
                case "1":
                    mr = BrowseSubjectMenu(member);
                    break;
                case "2":
                    mr = SearchByAuthorTitleMenu(member);
                    break;
                case "3":
                    mr = ViewEditShoppingCartMenu(member);
                    break;
                case "4":
                    mr = OrdersMenu(member);
                    break;
                case "5":
                    mr = CheckOutMenu(member);
                    break;
                case "6":
                    mr = OneClickCheckOut(member);
                    break;
                case "7":
                    break;
                case "8":
                    return MenuReturn.MainMenu;
                default:
            }
            
            switch (mr) {
                case MemberMenu: continue;
                case MainMenu: return MenuReturn.MainMenu;
                case None: continue;
                default:
                    break outerloop;
            }
        }
        return MenuReturn.None;
    }
    
    static MenuReturn BrowseSubjectMenu(Member member) {
        System.out.println("Subjects:");
        List<String> subjects = dataHandler.SelectSubjects();
        for(int i = 0; i < subjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, subjects.get(i));
        }
        System.out.print("Select your choice: ");
        String s = scanner.nextLine();
        if(s.compareTo("1") >= 0 || s.compareTo(Integer.toString(subjects.size())) <= 0) {
            int choice = Integer.parseInt(s) - 1;
            return BooksBySubjectMenu(member, subjects.get(choice));
        }
        
        return MenuReturn.None;
    }
    
    static MenuReturn BooksBySubjectMenu(Member member, String subject) {
        
        PrintGap();
        
        List<Book> books = dataHandler.SelectBooks(null, null, null, subject);
        
        System.out.printf("There are %d books available on the subject: %s\n", books.size(), subject);
        
        return BrowseBooksMenu(member, books);
    }
    
    static MenuReturn AddToCartMenu(Member member) {
        System.out.print("Enter ISBN to add to CART or\n"
                + "\"n\" to continue to browse or\n"
                + "Press Enter to go back to menu: ");

        String s = scanner.nextLine();

        switch (s) {
            case "":
                return MenuReturn.MemberMenu;
            case "n":
                break;
            default:
                List<Book> book = dataHandler.SelectBooks(s, null, null, null);
                if (book.isEmpty()) {
                    System.out.println("That book isbn does not exist, press enter to continue to browse.");
                    scanner.nextLine();
                } else {
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();

                    if (qty > 0) {
                        dataHandler.AddCartItem(new CartItem(member.getUserid(), book.get(0).getIsbn(), book.get(0).getTitle(), book.get(0).getPrice(), qty));
                    }
                }
        }

        return MenuReturn.None;
    }
    
    static MenuReturn BrowseBooksMenu(Member member, List<Book> books) {
        int i = 0;
        while(i < books.size()) {
            books.get(i).Print();
            System.out.println();
            i++;
            
            if (i < books.size()) {
                books.get(i).Print();
                System.out.println();
                i++;
            }

            switch(AddToCartMenu(member)) {
                case MemberMenu: return MenuReturn.MemberMenu;
                
            }
        }
        
        return MenuReturn.None;
    }
    
    static MenuReturn SearchByAuthorTitleMenu(Member member) {
        PrintGap();
        System.out.println("1. Author Search");
        System.out.println("2. Title Search");
        System.out.println("3. Go Back to Member Menu");
        System.out.print("\nSelect your option: ");
        String s = scanner.nextLine();
        switch(s) {
            case "1":
                return SearchByAuthorMenu(member);
            case "2":
                return SearchByTitleMenu(member);
            case "3":
                return MenuReturn.MemberMenu;
            default:
        }
        
        return MenuReturn.None;
    }
    
    static MenuReturn SearchByAuthorMenu(Member member) {
        System.out.print("Enter you search term for the author: ");
        String s = scanner.nextLine();
        List<Book> books = dataHandler.SelectBooks(null, s, null, null);
        System.out.printf("%d books found\n", books.size());
        
        return BrowseBooksMenu(member, books);
    }
    
    static MenuReturn SearchByTitleMenu(Member member) {
        System.out.print("Enter you search term for the title: ");
        String s = scanner.nextLine();
        List<Book> books = dataHandler.SelectBooks(null, null, s, null);
        System.out.printf("%d books found\n", books.size());
        
        return BrowseBooksMenu(member, books);
    }
    
    static MenuReturn ViewEditShoppingCartMenu(Member member) {
        PrintGap();
        System.out.println("Current Card Contents:");
        List<CartItem> cartItems = dataHandler.SelectCartItems(member.getUserid());
        ItemDetails.Print(cartItems);
//        System.out.printf("%-12s%-40s%-8s%-4s%-6s\n", "ISBN", "Title", "$", "QTY", "Total");
//        System.out.println("----------------------------------------------------------------------");
//        
//        
//        double total = 0;
//        for(CartItem c : cartItems) {
//            total += c.getTotal();
//            System.out.printf("%-12s%-40s%-8s%-4s%-6s\n", 
//                    c.getIsbn(), 
//                    c.getTitle().substring(0, Math.min(c.getTitle().length(), 38)), 
//                    String.format("%.2f",c.getPrice()), 
//                    Integer.toString(c.getQty()), 
//                    String.format("%.2f",c.getTotal()));
//        }
//        System.out.println("----------------------------------------------------------------------");
//        System.out.printf("%-52s%-8s\n\n", "Total", String.format("$%.2f",total));
        
        System.out.println("d. Delete Item");
        System.out.println("e. Edit Cart");
        System.out.println("q. Go back to Menu");
        System.out.print("Select your choice: ");
        
        String s = scanner.nextLine();
        
        switch(s) {
            case "d":
                System.out.print("Enter isbn of item: ");
                s = scanner.nextLine();
                if(dataHandler.DeleteCartItem(member.getUserid(), s)) {
                    System.out.println("Item Deleted From cart. Press enter to go back to menu.");
                } else {
                    System.out.println("Could not find item in cart. Press enter to go back to menu.");
                }
                scanner.nextLine();
                break;
            case "e":
                System.out.print("Enter isbn of item: ");
                s = scanner.nextLine();
                System.out.print("Enter new quantity: ");
                int qty = scanner.nextInt();
                scanner.nextLine();
                boolean result;
                if(qty <= 0) {
                    result = dataHandler.DeleteCartItem(member.getUserid(), s);
                } else {
                    result = dataHandler.UpdateCartItem(member.getUserid(), s, qty);
                }
                
                if(result) {
                    System.out.println("Edit Item Completed.");
                } else {
                    System.out.println("Could not find item in cart.");
                }
                
                System.out.println("Press enter to go back to menu.");
                scanner.nextLine();
                break;
            case "q":
                break;
            default:
        }
        
        return MenuReturn.MemberMenu;
    }
    
    static MenuReturn OrdersMenu(Member member) {
        System.out.println("Orders placed by " + member.getFname() + " " + member.getLname());
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-12s%-15s%-15s\n", "ORDER NO", "RECEIVED DATE", "SHIPPED DATE");
        System.out.println("----------------------------------------------------------------------");
        
        List<Order> orders = dataHandler.SelectOrders(member.getUserid());
        for(Order o: orders) {
            System.out.printf("%-12s%-15s%-15s\n", Integer.toString(o.getOno()), o.getReceived().toString(), "N/A");
        }
        
        System.out.println("----------------------------------------------------------------------");
        System.out.print("Enter the Order No to display its details or (q) to quit: ");
        String s = scanner.nextLine();
        
        if(s.equals("q")) {
            return MenuReturn.MemberMenu;
        }
        
        try {
            int no = Integer.parseInt(s);
            return OrderDetailsMenu(member, no);
            
        } catch (NumberFormatException e) {
            System.out.println("Order not found. Press enter to return to menu.");
            scanner.nextLine();
        }
        
        return MenuReturn.None;
    }
    
    static MenuReturn OrderDetailsMenu(Member member, int ono) {
        Order order = dataHandler.SelectOrder(ono);
        List<Odetails> odetails = dataHandler.SelectOdetails(ono);
        if(order == null || odetails.isEmpty()) {
            System.out.println("Order not found. Press enter to return to menu.");
        } else {
            System.out.println("Details for Order no." + ono);
            order.Print(member, odetails);
//            System.out.printf("%1$-35s%1$-35s\n", "Shipping Address", "Billing address");
//            System.out.printf("%-35s%-35s\n", String.format("%s %s", member.getFname(), member.getLname()));
//            System.out.printf("%-35s%-35s\n", String.format("Address: %s", order.getShipAddress()), String.format("Address: %s", member.getAddress()));
//            System.out.printf("%-35s%-35s\n", order.getShipCity(), member.getCity());
//            System.out.printf("%-35s%-35s\n", String.format("%s %d", order.getShipState(), order.getShipZip()), String.format("%s %d", member.getState(), member.getZip()));
//            System.out.println("----------------------------------------------------------------------");
//            
//            ItemDetails.Print(odetails);
//            ItemDetails.PrintHeader();
//            double total = 0;
//            for (Odetails o : odetails) {
//                total += o.getTotal();
//                o.Print();
////                System.out.printf("%-12s%-40s%-8s%-4s%-6s\n",
////                        o.getIsbn(),
////                        o.getTitle().substring(0, Math.min(o.getTitle().length(), 38)),
////                        String.format("%.2f", o.getPrice()),
////                        Integer.toString(o.getQty()),
////                        String.format("%.2f", o.getTotal()));
//            }
//            System.out.println("----------------------------------------------------------------------");
//            System.out.printf("%-52s%-8s\n\n", "Total", String.format("$%.2f", total));
            
            System.out.print("Press Enter to go back to Menu");
        }

        
        scanner.nextLine();
        return MenuReturn.MemberMenu;
    }
        
    static MenuReturn CheckOutMenu(Member member) {
        System.out.println("Current Cart Contents");
        
        List<CartItem> cartItems = dataHandler.SelectCartItems(member.getUserid());
        ItemDetails.Print(cartItems);
        
        System.out.print("Proceed to check out(y/n): ");
        String s = scanner.nextLine();
        if(!s.equals("y"))
            return MenuReturn.MemberMenu;
        
        String fname = member.getFname();
        String lname = member.getLname();
        String address = member.getAddress();
        String city = member.getCity();
        String state = member.getState();
        int zip = member.getZip();
        System.out.print("Do you want to enter new shipping address (y/n): ");
        s = scanner.nextLine();
        if (s.equals("y")) {
            System.out.print("Enter first name: ");
            fname = scanner.nextLine();
            System.out.print("Enter last name: ");
            lname = scanner.nextLine();
            System.out.print("Enter street address: ");
            address = scanner.nextLine();
            System.out.print("Enter city: ");
            city = scanner.nextLine();
            System.out.print("Enter state: ");
            state = scanner.nextLine();
            System.out.print("Enter zip: ");
            zip = scanner.nextInt();
            scanner.nextLine();
        }
        
        String creditcardtype;
        String creditcardnumber;
        System.out.print("Do you want to enter a new CreditCard (y/n): ");
        s = scanner.nextLine();
        if (s.equals("y")) {
            System.out.print("Enter type of Credit Card(amex/visa): ");
            creditcardtype = scanner.nextLine();
            System.out.print("Enter Credit Card Number: ");
            creditcardnumber = scanner.nextLine();
        }
        
        int odo = dataHandler.GenerateNewOrderNo();
        Order order = new Order(member.getUserid(), odo, new Date(Calendar.getInstance().getTime().getTime()), null, address, city, state, zip);
        List<Odetails> odetails = new ArrayList();
        for(CartItem c : cartItems) {
            odetails.add(new Odetails(odo, c.getIsbn(), c.getTitle(), c.getQty(), c.getPrice()));
        }
        
        if (dataHandler.GenerateOrder(order, odetails)) {
           System.out.println("Invoice for Order no." + order.getOno());
           order.Print(member, odetails);
        } else {
            System.out.println("Error Generating Order.");
        }

        System.out.println("Press enter to go back to Menu.");
        scanner.nextLine();
        return MenuReturn.MemberMenu;
    }
    
    static MenuReturn OneClickCheckOut(Member member) {
        List<CartItem> cartItems = dataHandler.SelectCartItems(member.getUserid());

        String fname = member.getFname();
        String lname = member.getLname();
        String address = member.getAddress();
        String city = member.getCity();
        String state = member.getState();
        int zip = member.getZip();

        int odo = dataHandler.GenerateNewOrderNo();
        Order order = new Order(member.getUserid(), odo, new Date(Calendar.getInstance().getTime().getTime()), null, address, city, state, zip);
        List<Odetails> odetails = new ArrayList();
        for(CartItem c : cartItems) {
            odetails.add(new Odetails(odo, c.getIsbn(), c.getTitle(), c.getQty(), c.getPrice()));
        }
        
        if (dataHandler.GenerateOrder(order, odetails)) {
           System.out.println("Invoice for Order no." + order.getOno());
           order.Print(member, odetails);
        } else {
            System.out.println("Error Generating Order.");
        }

        System.out.println("Press enter to go back to Menu.");
        scanner.nextLine();
        
        return MenuReturn.MemberMenu;
    }
}
