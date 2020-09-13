package com.sigruptor.datastructure.queue;

import java.util.Objects;

/**
 * @author abhishek_jhanwar on 9/12/20
 * <p>
 * This is a circular Queue Implementation
 **/
public class CircularQueue<T> {

    private final int CAPACITY;
    T[] elems;
    int curHead = 0;
    int curTail = -1;
    int count = 0;

    public CircularQueue(int capacity) {
        this.CAPACITY = capacity;
        elems = (T[]) new Objects[capacity];
    }

    public int size() {
        return count;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = elems[curHead];
        count--;
        curHead = (curHead + 1) % this.CAPACITY;
        return item;
    }

    public T seek() {
        if (isEmpty()) {
            return null;
        }
        return elems[curHead];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return size() >= this.CAPACITY;
    }

    public boolean enqueue(T item) {
        if (!isFull()) {
            curTail = (curTail + 1) % this.CAPACITY;
            elems[curTail] = item;
            count++;
            return true;
        }
        return false;
    }
}
