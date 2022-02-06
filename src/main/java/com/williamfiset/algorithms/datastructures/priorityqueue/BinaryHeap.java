/**
 * A min priority queue implementation using a binary heap.
 *
 * @author Heydar Rzayev, rzaaeeff@gmail.com
 */
package com.williamfiset.algorithms.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class BinaryHeap<T extends Comparable<T>> {

    // A dynamic list to track the elements inside the heap
    private List<T> heap = null;

    // Construct and initially empty priority queue
    public BinaryHeap() {
        this(10);
    }

    // Construct a priority queue with an initial capacity
    public BinaryHeap(int sz) {
        if (sz < 0) throw new IllegalArgumentException();
        else if (sz < 1) sz = 1;

        heap = new ArrayList<>(sz);
    }

    // Construct a priority queue using heapify in O(n) time, a great explanation can be found at:
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    public BinaryHeap(T[] elems) {
        this(elems.length);

        heap.addAll(Arrays.asList(elems));

        // heapify
        for (int i = Math.max(0, (heap.size() - 1) / 2); i >= 0; i--) {
            sink(i);
        }
    }

    // Priority queue construction, O(n)
    public BinaryHeap(Collection<T> elems) {
        this((T[]) elems.toArray());
    }

    // Returns true/false depending on if the priority queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Clears everything inside the heap, O(n)
    public void clear() {
        heap.clear();
    }

    // Return the size of the heap
    public int size() {
        return heap.size();
    }

    // Removes the root of the heap, O(log(n))
    public T poll() {
        if (heap.isEmpty()) throw new RuntimeException();

        T root = heap.get(0);
        swap(0, heap.size() - 1);
        sink(0);

        return root;
    }

    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    // Test if an element is in heap, O(n)
    public boolean contains(T elem) {
        return heap.contains(elem);
    }

    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    public void add(T elem) {
        heap.add(elem);
        swim(heap.size() - 1);
    }

    // Tests if the value of node i <= node j
    // This method assumes i & j are valid indices, O(1)
    private boolean less(int i, int j) {
        return heap.get(i).compareTo(heap.get(j)) <= 0;
    }

    // Perform bottom up node swim, O(log(n))
    private void swim(int k) {
        int parent = (k - 1) / 2;

        while (k > 0 && less(k, parent)) {
            swap(parent, k);
            k = parent;

            parent = (k - 1) / 2;
        }
    }

    // Top down node sink, O(log(n))
    private void sink(int k) {
        while (true) {
            int left = 2 * k + 1;
            int right = 2 * (k + 1);
            int smallest = k;

            if (left < heap.size() && !less(smallest, left)) smallest = left;
            if (right < heap.size() && !less(smallest, right)) smallest = right;

            if (smallest == k) break;

            swap(k, smallest);
            k = smallest;
        }
    }

    // Swap two nodes. Assumes i & j are valid, O(1)
    private void swap(int i, int j) {
        Collections.swap(heap, i, j);
    }

    // Removes a particular element in the heap, O(n)
    public boolean remove(T element) {
        int index = heap.indexOf(element);
        if (index < 0) return false;

        removeAt(index);
        return true;
    }

    // Removes a node at particular index, O(log(n))
    private T removeAt(int i) {
        if (heap.isEmpty()) return null;

        T removedData = heap.get(i);
        int last = heap.size() - 1;

        // Obliterate the value
        heap.remove(last);

        // Check if the last element was removed
        if (i == last) return removedData;

        T elem = heap.get(i);

        sink(i);

        if (heap.get(i).equals(elem)) swim(i);

        return removedData;
    }

    // Recursively checks if this heap is a min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is still being maintained
    // Called this method with k=0 to start at the root
    public boolean isMinHeap(int k) {
        if (k >= heap.size()) return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        if (left < heap.size() && !less(k, left)) return false;
        if (right < heap.size() && !less(k, right)) return false;

        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
