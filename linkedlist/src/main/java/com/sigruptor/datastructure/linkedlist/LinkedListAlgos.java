package com.sigruptor.datastructure.linkedlist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author abhishek_jhanwar on 9/6/20
 *
 * 1. Reverse LinkedList Recurisve/Iterative
 * 2. Reverse in groups of K
 * 3. Detect if there is a loop in list and find start of the loop
 * 4.
 **/
public class LinkedListAlgos {

    private static final Logger logger = LogManager.getLogger(LinkedListAlgos.class);

    public LinkedList reverse(LinkedList head) {
        if (head == null) {
            return null;
        }
        LinkedList.Node nodeHead = head.getHead();

        head.setHead(reverse(nodeHead));
        return head;
    }

    private LinkedList.Node reverse(LinkedList.Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        LinkedList.Node reversedList = reverse(head.next);

        LinkedList.Node next = head.next;
        next.next = head;
        head.prev = next;
        head.next = null;
        return reversedList;
    }

    public LinkedList reverseIterative(LinkedList head) {
        if (head == null || head.getHead().next == null) {
            return head;
        }

        LinkedList.Node nodeHead = head.getHead();
        LinkedList.Node prev = null;
        LinkedList.Node next;
        while (nodeHead != null) {
            next = nodeHead.next;
            nodeHead.next = prev;
            if (prev!=null) {
                prev.prev = nodeHead;
            }
            prev = nodeHead;
            nodeHead = next;
        }

        head.setHead(prev);
        return head;
    }

    public LinkedList reverseKGroups(LinkedList head, int K) {
        if (head == null) {
            return null;
        }
        head.setHead(reverseKGroups(head.getHead(), K));
        return head;
    }

    private LinkedList.Node reverseKGroups(LinkedList.Node head, int K) {
        if (head == null || head.next == null) {
            return head;
        }
        int count = 0;
        LinkedList.Node next = null;
        LinkedList.Node prev = null;
        LinkedList.Node node = head;
        while (count < K && node!=null) {
            next = node.next;
            node.next = prev;
            prev = node;
            if (prev != null) {
                prev.prev = node;
            }
            node = next;
            count++;
        }
        head.next = reverseKGroups(node, K);
        if (head.next != null) {
            head.next.prev = head;
        }
        return prev;
    }

    public LinkedList.Node detectLoop(LinkedList head) {
        if (head == null) {
            return null;
        }
        LinkedList.Node fast = head.getHead();
        LinkedList.Node slow = head.getHead();
        do {
            fast = fast.next;
            if (fast == null) {
                logger.info("Fast pointer became null, does not contain a loop");
                return null;
            }
            fast = fast.next;
            slow = slow.next;
        } while (fast != slow && fast!=null && slow != null);

        if (slow == null || fast == null) {
            logger.info("Fast pointer became null, does not contain a loop");
            return null;
        }
        logger.info("Loop exists and they meet at: {}", slow.val);
        // point of intersection
        slow = head.getHead();
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow != null ? slow : null;
    }
}
