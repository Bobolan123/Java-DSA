import java.util.Scanner;

public class OnlineBookstore {
    private ArrayListADT<Book> bookList = new ArrayListADT<>();
    private QueueADT<Order> orderQueue = new QueueADT<>();
    private Order currentOrder;
    private StackADT<Order> orderHistory = new StackADT<>();

    public OnlineBookstore() {
        // Initialize the book list
        bookList = new ArrayListADT<>();

        // Hardcode 10 books
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 10.99));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 8.99));
        bookList.add(new Book("1984", "George Orwell", 6.99));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 7.49));
        bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 9.49));
        bookList.add(new Book("Moby-Dick", "Herman Melville", 11.99));
        bookList.add(new Book("War and Peace", "Leo Tolstoy", 13.99));
        bookList.add(new Book("The Odyssey", "Homer", 12.99));
        bookList.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", 10.49));
        bookList.add(new Book("The Hobbit", "J.R.R. Tolkien", 8.49));
    }

    public static void main(String[] args) {
        OnlineBookstore app = new OnlineBookstore();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int option = getValidatedIntInput(scanner);

            switch (option) {
                case 1:
                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter your shipping address: ");
                    String userAddress = scanner.nextLine();

                    currentOrder = new Order(userName, userAddress);
                    userMenu(scanner);
                    break;

                case 2:
                    adminMenu(scanner);
                    break;

                case 3:
                    System.out.println("Exiting the system...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Display all books");
            System.out.println("2. Add book to order");
            System.out.println("3. Search book by ID");
            System.out.println("4. Search book by title");
            System.out.println("5. Display current order");
            System.out.println("6. Proceed to payment");
            System.out.println("7. View order history");
            System.out.println("8. Logout");
            System.out.print("Select an option: ");

            int option = getValidatedIntInput(scanner);

            switch (option) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    addBookToOrder(scanner);
                    break;
                case 3:
                    searchBookById(scanner);
                    break;
                case 4:
                    searchBookByTitle(scanner);
                    break;
                case 5:
                    if (currentOrder != null) {
                        currentOrder.displayBooks();
                    } else {
                        System.out.println("No current order.");
                    }
                    break;
                case 6:
                    proceedToPayment();
                    break;
                case 7:
                    viewOrderHistory();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View all books");
            System.out.println("2. Add new book");
            System.out.println("3. Search book by title");
            System.out.println("4. Update a book");
            System.out.println("5. Delete a book");
            System.out.println("6. View order queue");
            System.out.println("7. Logout");
            System.out.print("Select an option: ");

            int option = getValidatedIntInput(scanner);

            switch (option) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    createNewBook(scanner);
                    break;
                case 3:
                    searchBookByTitle(scanner);
                    break;
                case 4:
                    updateBook(scanner);
                    break;
                case 5:
                    deleteBook(scanner);
                    break;
                case 6:
                    viewOrderQueue();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void updateBook(Scanner scanner) {
        System.out.print("Enter the book ID to update: ");
        int bookId = getValidatedIntInput(scanner);

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.println("\nUpdating book: " + book);
        System.out.print("Enter new title (or press Enter to skip): ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            book.setTitle(newTitle);
        }

        System.out.print("Enter new author (or press Enter to skip): ");
        String newAuthor = scanner.nextLine().trim();
        if (!newAuthor.isEmpty()) {
            book.setAuthor(newAuthor);
        }

        System.out.print("Enter new price (or press Enter to skip): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(priceInput);
                book.setPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price input. Update skipped.");
            }
        }

        System.out.println("Book updated successfully!");
    }

    private void deleteBook(Scanner scanner) {
        System.out.print("Enter the book ID to delete: ");
        int bookId = getValidatedIntInput(scanner);

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        // Remove the book from the bookList
        boolean isRemoved = bookList.remove(book);
        if (isRemoved) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Failed to delete the book.");
        }
    }

    private void displayAllBooks() {
        Book[] booksArray = bookListToArray();
        Sorting.insertionSortById(booksArray);

        System.out.println("\n--- Book List (Sorted by Title) ---");
        for (Book book : booksArray) {
            System.out.println(book);
        }
    }

    private void searchBookById(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int bookId = getValidatedIntInput(scanner);

        Book[] booksArray = bookListToArray();
        Sorting.insertionSortById(booksArray);

        Book book = Searching.binarySearchById(booksArray, bookId);
        if (book != null) {
            System.out.println("Book found: " + book);
        } else {
            System.out.println("Book not found.");
        }
    }

    private void searchBookByTitle(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter a search key: ");
        String key = scanner.nextLine();

        Book[] booksArray = bookListToArray();
        Searching.linearSearchByTitle(booksArray, key);
    }

    private void viewOrderQueue() {
        System.out.println("\n--- Current Order Queue ---");

        if (orderQueue.isEmpty()) {
            System.out.println("The order queue is empty.");
        } else {
            QueueADT<Order> tempQueue = new QueueADT<>();
            while (!orderQueue.isEmpty()) {
                Order order = orderQueue.dequeue();
                System.out.println("Username: " + order.getUsername());
                System.out.println("Shipping Address: " + order.getShippingAddress());

                if (order.getBooks().isEmpty()) {
                    System.out.println("No books in this order.");
                } else {
                    order.displayBooks();
                }

                tempQueue.enqueue(order);
            }
            while (!tempQueue.isEmpty()) {
                orderQueue.enqueue(tempQueue.dequeue());
            }
        }
    }

    private void addBookToOrder(Scanner scanner) {
        System.out.print("Enter the book ID to add to your order: ");
        int bookId = getValidatedIntInput(scanner);

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found!");
        } else {
            currentOrder.addBook(book);
            System.out.println("Book added to order.");
        }
    }

    private void proceedToPayment() {
        if (currentOrder.getBooks().isEmpty()) {
            System.out.println("No books in the current order.");
        } else {
            // Create a new Order instance to avoid mutating the current order reference
            Order orderToQueue = new Order(currentOrder.getUsername(), currentOrder.getShippingAddress());
            ArrayListADT<Book> copiedBooks = new ArrayListADT<>();
            for (int i = 0; i < currentOrder.getBooks().size(); i++) {
                copiedBooks.add(currentOrder.getBooks().get(i));
            }
            orderToQueue.setBooks(copiedBooks);

            // Enqueue the order to the orderQueue
            orderQueue.enqueue(orderToQueue);

            // Push the completed order to the orderHistory stack
            orderHistory.push(orderToQueue);

            // Clear the current order books
            currentOrder.setBooks(new ArrayListADT<Book>());
            System.out.println("Order placed successfully and added to order history!");
        }
    }

    private void viewOrderHistory() {
        System.out.println("\n--- Order History ---");

        if (orderHistory.isEmpty()) {
            System.out.println("No previous orders found.");
        } else {
            Order recentOrder = orderHistory.pop();
            System.out.println("Most recent order:");
            System.out.println("Username: " + recentOrder.getUsername());
            System.out.println("Shipping Address: " + recentOrder.getShippingAddress());
            if (recentOrder.getBooks().isEmpty()) {
                System.out.println("No books in this order.");
            } else {
                recentOrder.displayBooks();
            }
            System.out.println("Order removed from history.");
        }
    }

    private void createNewBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = getValidatedDoubleInput(scanner);

        Book newBook = new Book(title, author, price);
        bookList.add(newBook);
        System.out.println("Book added successfully!");
    }

    private Book findBookById(int id) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == id) {
                return bookList.get(i);
            }
        }
        return null;
    }

    private int getValidatedIntInput(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double getValidatedDoubleInput(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private Book[] bookListToArray() {
        Book[] booksArray = new Book[bookList.size()];
        for (int i = 0; i < bookList.size(); i++) {
            booksArray[i] = bookList.get(i);
        }
        return booksArray;
    }
}
