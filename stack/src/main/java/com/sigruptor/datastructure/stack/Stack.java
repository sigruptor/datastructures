package com.sigruptor.datastructure.stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abhishek_jhanwar on 8/27/20
 * Email: abhishek_jhanwar@apple.com
 **/
public class Stack<T> {
    private static final Logger logger = LogManager.getLogger(Stack.class);
    List<T> nodes;
    private int topIndex;
    private final int capacity;

    public Stack() {
        this(-1);
    }

    public Stack(int capacity) {
        this.capacity = capacity;
        this.nodes = new ArrayList();
        this.topIndex = -1;
    }

    public boolean push(T node) {
        if (capacity != -1 && topIndex >= capacity) {
            logger.error("Stack's capacity is full, cant add more elements");
            return false;
        }
        nodes.add(node);
        topIndex++;
        return true;
    }

    public T pop() {
        if (topIndex != -1) {
            T topElem = nodes.get(topIndex);
            nodes.remove(topIndex);
            topIndex--;
            return topElem;
        }
        return null;
    }

    public T seek() {
        if (topIndex != -1) {
            return nodes.get(topIndex);
        }
        return null;
    }

    public int size() {
        if (topIndex != -1) {
            return topIndex + 1;
        }
        return 0;
    }

    public boolean isEmpty() {
        return topIndex == -1;
    }
}
