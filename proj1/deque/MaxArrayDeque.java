package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comp;
    public MaxArrayDeque(Comparator<T> c){
        super();
        comp = c;
    }

    public T max(){
        return max(comp);
    }

    public T max(Comparator<T> c){
        if (this.isEmpty()){return null;}

        T maxitem = this.get(0);

        for (int i=0; i<this.size(); i++){
            if (c.compare(get(i), maxitem) > 0) {
                maxitem = get(i);
            }
        }
        return maxitem;
    }



}
