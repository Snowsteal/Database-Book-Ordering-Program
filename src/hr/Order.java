package hr;

import java.sql.Date;
import java.util.List;

public class Order {

    public String getUserid() {
        return userid;
    }

    public int getOno() {
        return ono;
    }

    public Date getReceived() {
        return received;
    }

    public Date getShipped() {
        return shipped;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public int getShipZip() {
        return shipZip;
    }

    String userid;
    int ono;
    Date received;
    Date shipped;
    String shipAddress;
    String shipCity;
    String shipState;
    int shipZip;

    public Order(String userid, int ono, Date received, Date shipped, String shipAddress, String shipCity, String shipState, int shipZip) {
        this.userid = userid;
        this.ono = ono;
        this.received = received;
        this.shipped = shipped;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipState = shipState;
        this.shipZip = shipZip;
    }
    
    public void Print(Member member, List<Odetails> odetails) {
        System.out.println("Details for Order no." + ono);
        System.out.printf("%-35s%-35s\n", "Shipping Address", "Billing address");
        System.out.printf("%1$-35s%1$-35s\n", String.format("%s %s", member.getFname(), member.getLname()));
        System.out.printf("%-35s%-35s\n", String.format("Address: %s", shipAddress), String.format("Address: %s", member.getAddress()));
        System.out.printf("%-35s%-35s\n", shipCity, member.getCity());
        System.out.printf("%-35s%-35s\n", String.format("%s %d", shipState, shipZip), String.format("%s %d", member.getState(), member.getZip()));
        System.out.println("----------------------------------------------------------------------");

        ItemDetails.Print(odetails);
    }
}
