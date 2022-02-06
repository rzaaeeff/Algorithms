package com.williamfiset.algorithms.datastructures.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class SetOfStacks<T> {
    private final List<Stack<T>> stacks = new ArrayList<>();
    private final int maxPileHeight;
    private int size = 0;

    public SetOfStacks(int maxPileHeight) {
        this.maxPileHeight = Math.max(maxPileHeight, 1);
    }

    public SetOfStacks() {
        this(10);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private Stack<T> lastStack() {
        return stacks.get(stacks.size() - 1);
    }

    public void push(T elem) {
        // check if the last stack is full
        if (stacks.isEmpty() || lastStack().size() == maxPileHeight) stacks.add(new Stack<>());

        lastStack().push(elem);
        size++;
    }

    public T pop() {
        // check if the last stack is empty
        if (!stacks.isEmpty() && lastStack().isEmpty()) stacks.remove(stacks.size() - 1);

        if (stacks.isEmpty()) throw new EmptyStackException();

        size--;
        return lastStack().pop();
    }

    public T popAt(int index) {
        Stack<T> temp = new Stack<>();

        // copy to temporary stack
        while (size() > index + 1) temp.push(pop());

        T poppedElem = pop();

        // copy back to our set of stacks
        while (!temp.isEmpty()) push(temp.pop());

        return poppedElem;
    }

    public T peek() {
        // check if the last stack is empty
        if (!stacks.isEmpty() && lastStack().isEmpty()) stacks.remove(stacks.size() - 1);

        if (stacks.isEmpty()) throw new EmptyStackException();

        return lastStack().peek();
    }

    public static void main(String[] args) {
        SetOfStacks<Integer> setOfStacks = new SetOfStacks<>();

        for (int i = 0; i < 100; i++) {
            setOfStacks.push(i);
            if (setOfStacks.size() != i + 1) throw new RuntimeException();
        }

        int popped = 0;
        for (int i = 0; i < 100; i++) {
            if (new Random().nextBoolean()) {
                if (setOfStacks.popAt(i + 1) == i + popped++) throw new RuntimeException();
            }
        }

//        for (int i = 99; i >= 0; i--) {
//            if (setOfStacks.peek() != i) throw new RuntimeException();
//            if (setOfStacks.size() != i + 1) throw new RuntimeException();
//            if (setOfStacks.pop() != i) throw new RuntimeException();
//        }
    }
}
