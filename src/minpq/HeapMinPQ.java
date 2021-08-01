package minpq;

import java.util.*;

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
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        pq.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        return pq.contains(item);
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
        pq.remove(min);
        return min.item();

    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        //double priorityValue
        pq.remove(item);
        pq.add(new PriorityNode<T>(item, priority));
    }

    @Override
    public int size() {
        return pq.size();
    }
}
