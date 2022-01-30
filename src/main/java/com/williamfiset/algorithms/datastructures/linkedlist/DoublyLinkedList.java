/**
 * A doubly linked list implementation.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.linkedlist;

public class DoublyLinkedList<T> implements Iterable<T> {

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    // Empty this linked list, O(n)
    public void clear() {
        Node<T> trav = head;

        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = null;
            trav.next = null;
            trav.data = null;
            trav = next;
        }

        head = tail = null;
        size = 0;
    }

    // Return the size of this linked list
    public int size() {
        return size;
    }

    // Is this linked list empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // Add an element to the tail of the linked list, O(1)
    public void add(T elem) {
        addLast(elem);
    }

    // Add a node to the tail of the linked list, O(1)
    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            tail.next = new Node<>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    // Add an element to the beginning of this linked list, O(1)
    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            head.prev = new Node<>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    private Node<T> getAt(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        Node<T> elementAtIndex;
        if (index <= size / 2) {
            // look for index in the left part
            elementAtIndex = head;
            for (int i = 0; i < index; i++) elementAtIndex = elementAtIndex.next;
        } else {
            // look for index in the right part
            elementAtIndex = tail;
            for (int i = size - 1; i > index; i--) elementAtIndex = elementAtIndex.prev;
        }
        return elementAtIndex;
    }

    // Add an element at a specified index
    public void addAt(int index, T data) {
        if (index == 0) addFirst(data);
        else if (index == size) addLast(data);
        else {
            Node<T> elementAtIndex = getAt(index);
            Node<T> prevElement = elementAtIndex.prev;
            prevElement.next = new Node<>(data, prevElement, elementAtIndex);
            elementAtIndex.prev = prevElement.next;

            size++;
        }
    }

    // Check the value of the first node if it exists, O(1)
    public T peekFirst() {
        return head == null ? null : head.data;
    }

    // Check the value of the last node if it exists, O(1)
    public T peekLast() {
        return tail == null ? null : tail.data;
    }

    // Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("List is empty");
        T data = head.data;
        head.data = null;
        head = head.next;
        size--;

        if (isEmpty()) tail = null;
        else head.prev = null;

        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("List is empty");
        T data = tail.data;
        tail.data = null;
        tail = tail.prev;
        size--;

        if (isEmpty()) head = null;
        else tail.next = null;

        return data;
    }

    // Remove an arbitrary node from the linked list, O(1)
    private T remove(Node<T> node) {
        if (isEmpty()) throw new RuntimeException("List is empty");
        else if (head.equals(node)) return removeFirst();
        else if (tail.equals(node)) return removeLast();
        else {
            T data = node.data;
            node.data = null;
            Node<T> prev = node.prev;
            Node<T> next = node.next;
            node.prev = null;
            node.next = null;
            prev.next = next;
            next.prev = prev;
            prev = next = null;
            size--;
            return data;
        }
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        if (index == 0) return removeFirst();
        else if (index == size - 1) return removeLast();
        else {
            Node<T> elementAtIndex = getAt(index);
            T data = elementAtIndex.data;
            Node<T> prev = elementAtIndex.prev;
            Node<T> next = elementAtIndex.next;

            // free memory
            elementAtIndex.data = null;
            elementAtIndex.prev = null;
            elementAtIndex.next = null;

            // link prev and next
            next.prev = prev;
            prev.next = next;

            size--;
            return data;
        }
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(Object obj) {
        if (isEmpty()) throw new RuntimeException("List is empty");
        else {
            Node<T> trav = head;
            if (obj == null) {
                while (trav != null) {
                    if (trav.data == null) {
                        remove(trav);
                        return true;
                    }
                    trav = trav.next;
                }
            } else {
                while (trav != null) {
                    if (obj.equals(trav.data)) {
                        remove(trav);
                        return true;
                    }
                    trav = trav.next;
                }
            }
            return false;
        }
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(Object obj) {
        Node<T> trav = head;
        int i = 0;
        if (obj == null) {
            while (trav != null) {
                if (trav.data == null) {
                    return i;
                }
                i++;
                trav = trav.next;
            }
        } else {
            while (trav != null) {
                if (obj.equals(trav.data)) {
                    return i;
                }
                trav = trav.next;
                i++;
            }
        }
        return -1;
    }

    // Check is a value is contained within the linked list
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                DoublyLinkedList.this.remove(trav);
            }
        };
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav.data);
            if (trav.next != null) sb.append(", ");
            trav = trav.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
