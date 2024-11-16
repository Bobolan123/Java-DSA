public class Book {
    private static int idCounter = 1;
    private int id;
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { // Setter for title
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { // Setter for author
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { // Setter for price
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Price: $" + price;
    }
}
