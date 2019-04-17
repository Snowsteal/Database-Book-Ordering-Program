package hr;

import java.util.List;


public class ItemDetails {

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public float getTotal() {
        return total;
    }
    
    String isbn;
    String title;
    float price;
    int qty;
    float total;

    public ItemDetails(String isbn, String title, float price, int qty) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.qty = qty;
        this.total = price * qty;
    }

    public void Print() {
        System.out.printf("%-12s%-40s%-8s%-4s%-6s\n",
                isbn,
                title.substring(0, Math.min(title.length(), 38)),
                String.format("%.2f", price),
                Integer.toString(qty),
                String.format("%.2f", total));
    }
    
    public static void PrintHeader() {
        System.out.printf("%-12s%-40s%-8s%-4s%-6s\n", "ISBN", "Title", "$", "QTY", "Total");
        System.out.println("----------------------------------------------------------------------");
    }
    
    public static void Print(List<? extends ItemDetails> items) {
        ItemDetails.PrintHeader();
        double total = 0;
        for (ItemDetails i : items) {
            total += i.getTotal();
            i.Print();
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-52s%-8s\n\n", "Total", String.format("$%.2f", total));
    }
}
