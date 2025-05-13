package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {

    private T[] items;
    private int size;
    private int nextfirst;
    private int nextlast;

    public ArrayDeque() {
        size = 0;
        items =(T[]) new Object[8];
        nextfirst = 4;
        nextlast= 5;
    }

    public int index(int i){
        int ith = nextfirst + i + 1;
        if (ith < items.length){
            return ith;
        } else {
            return (ith - items.length);
        }
    }
    public void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        for (int i=0; i<size; i++){
            a[i] = items[index(i)];
        }
        items = a;
        nextfirst = items.length-1;
        nextlast = size;
    }

    @Override
    public void addFirst(T item){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextfirst] = item;
        if (nextfirst == 0){
            nextfirst = items.length-1;
        } else {
            nextfirst -= 1;
        }
        size += 1;
    }

    @Override
    public void addLast(T item){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextlast] = item;
        if (nextlast == items.length-1){
            nextlast = 0;
        } else {
            nextlast += 1;
        }
        size += 1;
    }


    /* Returns the number of items in the deque. */
    @Override
    public int size(){
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque(){
        for (int i=0; i<size; i++){
            System.out.print(items[index(i)] + " ");
        }
    }

    /*Removes and returns the item at the front of the deque*/
    @Override
    public T removeFirst(){
        if (size == 0) {return null;}
        if ((size < items.length/4) && (items.length > 8)){
            resize(items.length / 2 );
        }
        T item = items[index(0)];
        items[index(0)] = null;
        nextfirst = index(0);
        size -= 1;
        return item;
    }

    @Override
    public T removeLast(){
        if (size == 0) {return null;}
        if ((size < items.length/4) && (items.length > 8)){
            resize(items.length / 2 );
        }
        T item = items[index(size-1)];
        items[index(size-1)] = null;
        nextlast = index(size-1);
        size -= 1;
        return item;
    }

    @Override
    public T get(int ith){
        int a = index(ith);
        return items[a];
    }

    @Override
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;
        public ArrayDequeIterator() {
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
