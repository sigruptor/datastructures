package com.sigruptor.datastructure.queue;

/**
 * @author abhishek_jhanwar on 9/12/20
 **/
public class Queue<T> {

    private int CAPACITY;
    private int tail = -1;
    private int head = 0;
    private int count = 0;

    private T[] items;

    private final int DEFAULT_CAPACITY = 12;
    private int CUR_CAPACITY = DEFAULT_CAPACITY;

    public Queue() {
        this(-1);
    }

    public Queue(int capacity) {
        this.CAPACITY = capacity;
        if (capacity == -1) {
            items = (T[]) new Object[DEFAULT_CAPACITY];
        } else {
            items = (T[]) new Object[CAPACITY];
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        if (this.CAPACITY == -1) {
            return size() >= this.DEFAULT_CAPACITY;
        } else {
            return size() >= this.CAPACITY;
        }
    }

    public boolean ensureCapacity() {
        if (isFull() && this.CAPACITY == -1) {
            this.CUR_CAPACITY = 2*CUR_CAPACITY;
            T[] newItems = (T[]) new Object[CUR_CAPACITY];
            int newTail=-1;
            int newHead = 0;
            for (int i=head;i<=tail;i++) {
                newItems[++newTail] = items[i];
            }
            this.head = newHead;
            this.tail = newTail;
            this.items = newItems;
            return true;
        }
        return false;
    }

    public boolean enqueue(T item) {
        if (isFull() && !ensureCapacity()) {
            return false;
        }
        items[++tail] = item;
        this.count++;
        return true;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = items[head++];
        count--;
        return item;
    }
}
