/**
 * A linked list implementation of a stack
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.stack;

import java.util.EmptyStackException;
import java.util.Iterator;

public class ListStack<T> implements Iterable<T>, Stack<T> {

  private static class StackNode<T> {
    T data;
    StackNode<T> next;

    public StackNode(T data) {
      this.data = data;
    }

    public StackNode(T data, StackNode<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  private StackNode<T> head;
  private int size = 0;

  // Create an empty stack
  public ListStack() {}

  // Create a Stack with an initial element
  public ListStack(T firstElem) {
    push(firstElem);
  }

  // Return the number of elements in the stack
  public int size() {
    return size;
  }

  // Check if the stack is empty
  public boolean isEmpty() {
    return size() == 0;
  }

  // Push an element on the stack
  public void push(T elem) {
    if (isEmpty()) {
      head = new StackNode<>(elem);
    } else {
      head = new StackNode<>(elem, head);
    }

    size++;
  }

  // Pop an element off the stack
  // Throws an error is the stack is empty
  public T pop() {
    if (isEmpty()) throw new EmptyStackException();

    StackNode<T> poppedNode = head;
    head = head.next;
    poppedNode.next = null;
    size--;

    return poppedNode.data;
  }

  // Peek the top of the stack without removing an element
  // Throws an exception if the stack is empty
  public T peek() {
    if (isEmpty()) throw new EmptyStackException();
    return head.data;
  }

  // Searches for the element starting from top of the stack
  // Returns -1 if the element is not present in the stack
  public int search(T elem) {
    StackNode<T> curr = head;
    int pos = 1;

    if (elem == null) {
      while (curr != null) {
        if (curr.data == null) return pos;
        curr = curr.next;
        pos++;
      }
    } else {
      while (curr != null) {
        if (elem.equals(curr.data)) return pos;
        curr = curr.next;
        pos++;
      }
    }

    return -1;
  }

  // Allow users to iterate through the stack using an iterator
  @Override
  public java.util.Iterator<T> iterator() {
    return new Iterator<T>() {
      StackNode<T> curr = head;

      @Override
      public boolean hasNext() {
        return curr != null;
      }

      @Override
      public T next() {
        T data = curr.data;
        curr = curr.next;
        return data;
      }
    };
  }
}
