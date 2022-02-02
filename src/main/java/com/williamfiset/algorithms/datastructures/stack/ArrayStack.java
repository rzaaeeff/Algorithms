package com.williamfiset.algorithms.datastructures.stack;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * @author liujingkun
 */
@SuppressWarnings("unchecked")
public class ArrayStack<T> implements Stack<T>, Iterable<T> {

    private T[] data;
    private int size;

    public ArrayStack() {
        data = (T[]) new Object[16];
        size = 0;
    }

    @Override
    public int size() {
      return size;
    }

    @Override
    public boolean isEmpty() {
      return size() == 0;
    }

    @Override
    public void push(T elem) {
      if (size == data.length) grow();
      data[size++] = elem;
    }

    private void grow() {
      int newLength;
      if (Integer.MAX_VALUE / 2 > data.length) newLength = data.length * 2;
      else newLength = Integer.MAX_VALUE;
      data = Arrays.copyOf(data, newLength);
    }

    @Override
    public T pop() {
      if (isEmpty()) throw new EmptyStackException();
      return data[--size];
    }

    @Override
    public T peek() {
      if (isEmpty()) throw new EmptyStackException();
      return data[size - 1];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
          int i = 0;

          @Override
          public boolean hasNext() {
            return i < size;
          }

          @Override
          public T next() {
            return data[i++];
          }
        };
    }
}
