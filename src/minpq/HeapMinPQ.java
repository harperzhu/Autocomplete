package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    @Override
    public void add(T item, double priority) {
        if (pq.contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        pq.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        PriorityNode<T> temp = new PriorityNode<>(item, 0);
        return pq.contains(temp);
    }

    @Override
    public T peekMin() {
        if (pq.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = pq.peek();
        return min.item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = pq.peek();
        return pq.remove().item();

    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        List<PriorityNode<T>> items = new ArrayList<>();
        PriorityNode<T> temp = new PriorityNode<>(item, 0);
        items.add(temp);
        temp.setPriority(priority);
    }

    @Override
    public int size() {
        int size=0;
        Iterator iterator = pq.iterator();
        while(iterator.hasNext()){
            size += 1;
        }
        return size;
    }

}
