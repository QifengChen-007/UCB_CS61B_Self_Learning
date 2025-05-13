package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int index(int i) {
        int idx = nextFirst + i + 1;
        if (idx < items.length) {
            return idx;
        } else {
            return idx - items.length;
        }
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = items[index(i)];
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[index(i)] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
        int firstIndex = index(0);
        T item = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
        int lastIndex = index(size - 1);
        T item = items[lastIndex];
        items[lastIndex] = null;
        nextLast = lastIndex;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[index(index)];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int position;

        ArrayDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T returnItem = get(position);
            position++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Deque<?>)) {
            return false;
        }

        Deque<?> other = (Deque<?>) o;
        if (this.size != other.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            T thisItem = this.get(i);
            Object otherItem = other.get(i);
            if (thisItem == null) {
                if (otherItem != null) {
                    return false;
                }
            } else {
                if (!thisItem.equals(otherItem)) {
                    return false;
                }
            }
        }

        return true;
    }
}
