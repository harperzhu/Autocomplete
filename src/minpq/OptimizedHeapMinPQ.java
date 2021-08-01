package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;
    /**
     * The number of elements in the heap.
     */
    private int size;
    private PriorityNode<T> minNode;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        minNode = new PriorityNode<T>(null, 9999);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        if(temp.priority() < minNode.priority()) {
            minNode = temp;
        }
        items.add(temp);
        itemToIndex.put(temp.item(), size + 1);
        size++;

    }

    @Override
    public boolean contains(T item) {
        return items.contains(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return minNode.item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        itemToIndex.remove(peekMin());
        items.remove(peekMin());
        return peekMin();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        double priorityValue = itemToIndex.get(item);
        items.remove(item);
        items.add(new PriorityNode<>(item,priorityValue));
    }

    @Override
    public int size() {
        size = items.size();
        return size;
    }
}
