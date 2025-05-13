package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{

    private class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private Node sentFront;
    private Node sentBack;
    private int size;

    public LinkedListDeque(){
        sentFront = new Node(null,null,null);
        sentBack = new Node(null,null,null);
        size = 0;
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    public LinkedListDeque(T x){
        sentFront = new Node(null,null,null);
        sentBack = new Node(null,null,null);
        Node first = new Node(x, sentBack, sentFront);
        sentFront.next = first;
        sentBack.prev = first;
        size = 1;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item, sentFront.next, sentFront);
        if (isEmpty()) {
            sentBack.prev = newNode;
        } else {
            sentFront.next.prev = newNode;
        }
        sentFront.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item, sentBack, sentBack.prev);
        if (isEmpty()) {
            sentFront.next = newNode;
        } else {
            sentBack.prev.next = newNode;
        }
        sentBack.prev = newNode;
        size++;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque(){
        Node p = sentFront.next;
        for (int i=0; i<size; i++){
            System.out.print(p.item + " ");
        }
    }

    /*Removes and returns the item at the front of the deque*/
    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        Node first = sentFront.next;
        T item = first.item;
        sentFront.next = first.next;
        first.next.prev = sentFront;
        size--;
        return item;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) return null;
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
        Node p = sentFront.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }
    @Override
    public int size(){
        return size;
    }


    public T getNode(Node Curr,int index){
        if (index == 0){return Curr.item;}
        return getNode(Curr, index-1);
    }

    public T getRecursive(int index){
        if (index <0 || index >=size){
            return null;
        }
        return getNode(sentFront.next, index);
    }

    /* returns an iterator into ME */
    @Override
    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int wizPos;
        public LinkedListDequeIterator() {
            wizPos = 0;
        }

        @Override
        public boolean hasNext(){
            return wizPos < size;
         }

         @Override
         public T next(){
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
         }
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if (!(o instanceof Deque)) {return false;}

        Deque<T>  a = (Deque<T>) o;
        if(this.size()!=a.size()){return false;}

        for (int i=0; i<size(); i++){
            if (a.get(i)!= this.get(i)){ return false;}
        }
        return true;
    }

}
