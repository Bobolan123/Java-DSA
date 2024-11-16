public class Order {
    private String shippingAddress;
    private String username;
    private ArrayListADT<Book> books;

    public Order(String username, String shippingAddress) {
        this.shippingAddress = shippingAddress;
        this.username = username;
        this.books = new ArrayListADT<>();
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    // Method to add a book to the order
    public void addBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            Book existingBook = books.get(i);
            if (existingBook.getId() == book.getId()) {
                // If the book already exists, update the quantity (could be a custom class to
                // store quantity as well)
                System.out.println("Book already added. You can modify quantity or keep it as is.");
                return;
            }
        }
        books.add(book); // Add new book if not present
    }

    // Method to get the list of books in the order
    public ArrayListADT<Book> getBooks() {
        return books;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBooks(ArrayListADT<Book> books) {
        this.books = books;
    }

    // Method to display all books in the order
    public void displayBooks() {
        if (books.size() == 0) {
            System.out.println("No books in this order.");
            return;
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i)); // Print the book details (requires Book's toString method)
        }
    }
}