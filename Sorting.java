public class Sorting {
    // Method to sort books by ID
    public static void insertionSortById(Book[] books) {
        for (int i = 1; i < books.length; i++) {
            Book key = books[i];
            int j = i - 1;

            while (j >= 0 && books[j].getId() > key.getId()) {
                books[j + 1] = books[j];
                j = j - 1;
            }
            books[j + 1] = key;
        }
    }

    // Method to sort books by title
    public static void insertionSort(Book[] books) {
        for (int i = 1; i < books.length; i++) {
            Book key = books[i];
            int j = i - 1;

            while (j >= 0 && books[j].getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books[j + 1] = books[j];
                j = j - 1;
            }
            books[j + 1] = key;
        }
    }
}
