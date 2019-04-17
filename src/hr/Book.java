package hr;

public class Book {

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getSubject() {
        return subject;
    }

    String isbn;
    String author;
    String title;
    float price;
    String subject;
    
    public Book(String isbn, String author, String title, float price, String subject) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.price = price;
        this.subject = subject;
    }
    
    public void Print() {
        System.out.printf("Author: %s\n", author);
        System.out.printf("Title: %s\n", title);
        System.out.printf("ISBN: %s\n", isbn);
        System.out.printf("Price: %.2f\n", price);
        System.out.printf("Subject: %s\n", subject);
    }
}
