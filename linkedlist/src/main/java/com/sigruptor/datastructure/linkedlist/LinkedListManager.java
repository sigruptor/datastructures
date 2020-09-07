package com.sigruptor.datastructure.linkedlist;

import com.sigruptor.datastructure.utils.AlgoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author abhishek_jhanwar on 9/6/20
 **/
public class LinkedListManager implements AlgoManager {

    private static final Logger logger = LogManager.getLogger(LinkedListManager.class);

    private LinkedList linkedList;
    private LinkedListAlgos linkedListAlgos;

    public static void main(String[] args) {
        logger.debug("Starting LinkedList Manager");
        LinkedListManager linkedListManager = new LinkedListManager();
        linkedListManager.init(args);
        linkedListManager.start();
        linkedListManager.stop();
        logger.info("Exiting LinkedList Manager");
    }

    @Override
    public boolean start() {

        linkedList.add(10);
        logger.info("Adding node: {}, size: {}", 10, linkedList.size());
        LinkedList.displayList(linkedList);

        linkedList.deleteNode(4);
        linkedList.deleteNode(10);
        logger.info("Deleting nodes: 4 and 10, size: {}",linkedList.size());
        logger.info("Could not delete node: 100, because it does not exist {}", linkedList.deleteNode(100));
        LinkedList.displayList(linkedList);
        logger.info("deleting head: {}", linkedList.deleteNode(1));
        LinkedList.displayList(linkedList);

        try {
            logger.info("Reversing Linked List");
            LinkedList reverse = linkedListAlgos.reverse(linkedList.clone());
            LinkedList.displayList(reverse);

            logger.info("Reversing Linked List Iteratively");
            linkedListAlgos.reverseIterative(reverse);
            LinkedList.displayList(reverse);

            LinkedList newList = new LinkedList();
            newList.add(0);
            LinkedList.displayList(linkedListAlgos.reverse(newList));
            LinkedList.displayList(linkedListAlgos.reverseIterative(newList));

            linkedList.add(10);
            LinkedList.displayList(linkedList);
            logger.info("Reverse List in groups of K, {} ", 3);
            LinkedList.displayList(linkedListAlgos.reverseKGroups(linkedList.clone(), 3));

            logger.info("Detecting loop in a list");
            LinkedList.Node loop = linkedListAlgos.detectLoop(linkedList);

            logger.info("Creating loop in a list");
            LinkedList loopList = linkedList.clone();
            logger.info("Trying to add loop, is request success: {}",loopList.addLoop(7));
            loop = linkedListAlgos.detectLoop(loopList);
            if (loop == null) {
                logger.error("could not detect loop");
            } else {
                logger.info("Detected loop at node: {}", loop.val);
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean init(String[] args) {
        this.linkedListAlgos = new LinkedListAlgos();
        this.linkedList = new LinkedList<Integer>();
        if (args.length == 0) {
            throw new IllegalArgumentException("Args not provided");
        }
        for (String arg:args) {
            linkedList.add(Integer.parseInt(arg));
        }
        LinkedList.displayList(linkedList);

        return true;
    }
}
