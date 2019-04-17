package hr;


public class CartItem extends ItemDetails {

    public String getUserid() {
        return userid;
    }

    String userid;

    public CartItem(String userid, String isbn, String title, float price, int qty) {
        super(isbn, title, price, qty);
        
        this.userid = userid;
    }
    
}
