package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;
    private PriorityNode<T> min;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
        min = new PriorityNode<>(null, 9999);

    }

    @Override
    public void add(T item, double priority) {
        PriorityNode<T> new_item = new PriorityNode<>(item, priority);
        if(new_item.priority()< min.priority()) {
            min = new_item;
        }
        if (!items.contains(new_item)) {
            items.add(new_item);
        }
    }

    @Override
    public boolean contains(T item) {
        PriorityNode<T> temp = new PriorityNode<>(item, 0);
        return items.contains(temp);
    }

    @Override
    public T peekMin() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = items.get(0);
        for(int i=0;i<items.size();i++){
            if(items.get(i).priority() < min.priority()){
                min = items.get(i);
            }
        }
        return min.item();
    }

    @Override
    public T removeMin() {
        if (items.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = items.get(0);
        for(PriorityNode<T> p :items){
            if(p.priority() < min.priority()){
                min = p;
            }
        }
        items.remove(min);
        return min.item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        PriorityNode<T> temp = new PriorityNode<T>(item,priority);
        for(int i=0; i<items.size();i++){
            if(items.get(i).equals(temp)){
                items.remove(items.get(i));
                items.add(new PriorityNode<>(item, priority));
            }
        }
    }


    @Override
    public int size() {
        int size = 0;
        for(int i=0; i<items.size();i++){
            size += 1;
        }
        return size;
    }
}
