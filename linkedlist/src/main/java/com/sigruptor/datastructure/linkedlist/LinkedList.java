package com.sigruptor.datastructure.linkedlist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author abhishek_jhanwar on 9/6/20
 **/
public class LinkedList<T> {
    private static final Logger logger = LogManager.getLogger(LinkedList.class);
    private final int CAPACITY;
    private Node<T> head;
    private Node<T> current;
    private int curSize;
    public LinkedList() {
        this(-1);
    }

    public LinkedList(int capacity) {
        this.CAPACITY = capacity;
        this.curSize = 0;
    }

    public static void displayList(LinkedList head) {
        if (head == null) {
            logger.info("Linked List is null");
        }
        Node nodeHead = head.head;
        StringBuilder sb = new StringBuilder();
        while (nodeHead != null) {
            sb.append(nodeHead.val);
            if (nodeHead.next != null) {
                sb.append("->");
                nodeHead = nodeHead.next;
            } else {
                break;
            }
        }
        logger.info("LinkedList: -> {}", sb.toString());
    }

    public boolean add(T val) {
        if (this.CAPACITY != -1 && this.curSize >= this.CAPACITY) {
            logger.error("LinkedList full, curSize={}, and total capacity = {}", curSize, CAPACITY);
            return false;
        }
        if (head == null) {
            this.head = new Node<>(val);
            this.current = head;
        } else {
            Node node = new Node<>(val);
            current.next = node;
            node.prev = current;
            current = node;
        }
        this.curSize++;
        return true;
    }

    public boolean deleteNode(T val) {
        Node<T> node = head;
        Node<T> prev = null;
        while (node != null) {
            if (node.val == val) {
                if (prev == null) {
                    head = node.next;
                } else {
                    prev.next = node.next;
                }
                if (node.next != null) {
                    node.next.prev = prev;
                }
                if (current == node) {
                    current = node.prev;
                }
                this.curSize--;
                break;
            } else {
                prev = node;
                node = node.next;
            }
        }
        return node != null;
    }

    public int size() {
        return this.curSize;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public T peek() {
        return current.val;
    }

    public int getCurSize() {
        return curSize;
    }

    public static class Node<T> {
        T val;
        Node<T> next;
        Node<T> prev;

        Node(T val) {
            this.val = val;
        }
    }

    public boolean addLoop(T val) {
        LinkedList.Node node = head;
        while (node != null) {
            if (node.val == val) {
                logger.info("Found node with value : {}", val);
                break;
            }
            node = node.next;
        }
        if (node != null) {
            current.next = node;
            return true;
        }
        return false;
    }

    @Override
    public LinkedList clone() throws CloneNotSupportedException {
        LinkedList newList = new LinkedList();
        LinkedList.Node nodeHead = this.head;
        while (nodeHead != null) {
            newList.add(nodeHead.val);
            nodeHead = nodeHead.next;
        }
        return newList;
    }
}
