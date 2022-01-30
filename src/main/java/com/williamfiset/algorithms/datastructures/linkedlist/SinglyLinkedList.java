package com.williamfiset.algorithms.datastructures.linkedlist;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
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
        Node<T> curr = head;
        while (curr != null) {
            Node<T> next = curr.next;
            curr.next = null;
            curr.data = null;
            curr = next;
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
        if (isEmpty()) head = tail = new Node<>(elem, null);
        else {
            tail.next = new Node<>(elem, null);
            tail = tail.next;
        }

        size++;
    }

    // Add an element to the beginning of this linked list, O(1)
    public void addFirst(T elem) {
        if (isEmpty()) head = tail = new Node<>(elem, null);
        else {
            head = new Node<>(elem, head);
        }

        size++;
    }

    // Add an element at a specified index
    public void addAt(int index, T data) throws Exception {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        else if (index == 0) addFirst(data);
        else if (index == size) addLast(data);
        else {
            Node<T> trav = head;
            for (int i = 0; i < index; i++) trav = trav.next;
            trav.next = new Node<>(data, trav.next);
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
        if (head == null) throw new NoSuchElementException();

        T data = head.data;
        head.data = null;
        head = head.next;
        size--;
        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        if (tail == null) throw new NoSuchElementException();

        T data = tail.data;

        if (size == 1) {
            tail.data = null;
            head = tail = null;
        } else {
            Node<T> trav = head;
            while (!trav.next.equals(tail)) trav = trav.next;
            trav.next = null;
            tail.data = null;
            tail = trav;
        }

        size--;
        return data;
    }

    // Remove an arbitrary node from the linked list, O(1)
    private T remove(Node<T> node) {
        if (node.equals(head)) return removeFirst();
        else if (node.equals(tail)) return removeLast();

        Node<T> trav = head;
        while (trav.next.equals(node)) trav = trav.next;
        Node<T> deleted = trav.next;
        T data = deleted.data;
        deleted.data = null;
        trav.next = deleted.next;
        deleted = trav = null;
        size--;

        return data;
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        else if (index == 0) return removeFirst();
        else if (index == size - 1) return removeLast();
        else {
            int i;
            Node<T> trav;
            for (i = 0, trav = head; i < index - 1; i++) trav = trav.next;
            Node<T> deleted = trav.next;
            T data = deleted.data;
            trav.next = deleted.next;
            deleted.data = null;
            trav = deleted = null;

            size--;
            return data;
        }
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(Object obj) {
        int index = indexOf(obj);

        if (index >= 0) {
            removeAt(index);
            return true;
        }

        return false;
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(Object obj) {
        if (isEmpty()) return -1;

        Node<T> trav = head;
        if (obj == null) {
            for (int i = 0; i < size; i++) {
                if (trav.data == null) return i;
                trav = trav.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (obj.equals(trav.data)) return i;
                trav = trav.next;
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
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                Node<T> curr = trav;
                trav = trav.next;
                return curr.data;
            }

            @Override
            public void remove() {
                SinglyLinkedList.this.remove(trav);
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
            if (trav.next != null) {
                sb.append(", ");
            }
            trav = trav.next;
        }
        sb.append("]");

        return sb.toString();
    }
}
