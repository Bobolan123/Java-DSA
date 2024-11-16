public class ArrayListADT<T> {
    private Object[] data;
    private int size;

    public ArrayListADT() {
        data = new Object[10];
        size = 0;
    }

    // Add a new element
    public void add(T item) {
        if (size == data.length) {
            resize();
        }
        data[size++] = item;
    }

    // Get an element by index
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) data[index];
    }

    // Get the current size
    public int size() {
        return size;
    }

    // Remove an element by index
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
    }

    // Remove an element by object
    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                remove(i); // Reuse the index-based remove method
                return true;
            }
        }
        return false; // Item not found
    }

    // Resize the internal array when needed
    private void resize() {
        Object[] newData = new Object[data.length * 2];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
