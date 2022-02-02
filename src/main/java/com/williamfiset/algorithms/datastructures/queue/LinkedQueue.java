/**
 * A simple queue implementation with a linkedlist
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<T> implements Iterable<T>, Queue<T> {

  private static class QueueNode<T> {
    T data;
    QueueNode<T> next;
    QueueNode<T> prev;

    public QueueNode(T data) {
      this.data = data;
    }

    public QueueNode(T data, QueueNode<T> prev, QueueNode<T> next) {
      this.data = data;
      this.next = next;
      this.prev = prev;
    }
  }

  private QueueNode<T> back;
  private QueueNode<T> front;
  private int size = 0;

  public LinkedQueue() {}

  public LinkedQueue(T firstElem) {
    offer(firstElem);
  }

  // Return the size of the queue
  public int size() {
    return size;
  }

  // Returns whether or not the queue is empty
  public boolean isEmpty() {
    return size() == 0;
  }

  // Peek the element at the front of the queue
  // The method throws an error is the queue is empty
  public T peek() {
    if (isEmpty()) throw new NoSuchElementException();
    return front.data;
  }

  // Poll an element from the front of the queue
  // The method throws an error is the queue is empty
  public T poll() {
    if (isEmpty()) throw new NoSuchElementException();
    QueueNode<T> polledNode = front;
    front = front.prev;
    if (front == null) back = null;
    polledNode.prev = polledNode.next = null;
    size--;
    return polledNode.data;
  }

  // Add an element to the back of the queue
  public void offer(T elem) {
    if (isEmpty()) front = back = new QueueNode<T>(elem);
    else {
      back.prev = new QueueNode<>(elem, null, back);
      back = back.prev;
    }

    size++;
  }

  // Return an iterator to alow the user to traverse
  // through the elements found inside the queue
  @Override
  public java.util.Iterator<T> iterator(){
    return new Iterator<T>() {
      QueueNode<T> curr = back;

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
