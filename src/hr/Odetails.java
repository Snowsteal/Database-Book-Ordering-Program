package hr;


public class Odetails extends ItemDetails{

    public int getOno() {
        return ono;
    }
    
    int ono;

    public Odetails(int ono, String isbn, String title, int qty, float price) {
        super(isbn, title, price, qty);
        
        this.ono = ono;
    }
    
}
