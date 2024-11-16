public class Searching {

    // Binary search for books by ID
    public static Book binarySearchById(Book[] books, int bookId) {
        int low = 0, high = books.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (books[mid].getId() == bookId) {
                return books[mid]; // Book found
            } else if (books[mid].getId() < bookId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Book not found
    }

    public static void linearSearchByTitle(Book[] books, String key) {
        boolean found = false;

        // Convert the search key to lowercase to ensure case-insensitive search
        key = key.toLowerCase();

        // Search through the book list
        for (Book book : books) {
            // Check if the book title contains the search key (case-insensitive)
            if (book.getTitle().toLowerCase().contains(key)) {
                // Print the book details if it matches
                System.out.println(book);
                found = true;
            }
        }

        // If no book matches the search, print a message
        if (!found) {
            System.out.println("No books found containing \"" + key + "\".");
        }
    }
}
