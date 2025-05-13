package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node {
        T item;
        Node next;
        Node prev;

        Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node sentFront;
    private Node sentBack;
    private int size;

    public LinkedListDeque() {
        sentFront = new Node(null, null, null);
        sentBack = new Node(null, null, null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
        size = 0;
    }

    public LinkedListDeque(T item) {
        this();
        Node first = new Node(item, sentBack, sentFront);
        sentFront.next = first;
        sentBack.prev = first;
        size = 1;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item, sentFront.next, sentFront);
        sentFront.next.prev = newNode;
        sentFront.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item, sentBack, sentBack.prev);
        sentBack.prev.next = newNode;
        sentBack.prev = newNode;
        size++;
    }

    @Override
    public void printDeque() {
        Node current = sentFront.next;
        while (current != sentBack) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node first = sentFront.next;
        T item = first.item;
        sentFront.next = first.next;
        first.next.prev = sentFront;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node last = sentBack.prev;
        T item = last.item;
        sentBack.prev = last.prev;
        last.prev.next = sentBack;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = sentFront.next;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return current.item;
            }
            current = current.next;
        }
        return null;
    }

    private T getNode(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getNode(node.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getNode(sentFront.next, index);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int position;

        LinkedListDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T item = get(position);
            position++;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<?> other = (Deque<?>) o;
        if (this.size() != other.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }

        return true;
    }
}
