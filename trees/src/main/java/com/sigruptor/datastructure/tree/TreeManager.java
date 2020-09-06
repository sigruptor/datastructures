package com.sigruptor.datastructure.tree;


import com.sigruptor.datastructure.utils.AlgoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author abhishek_jhanwar on 2020-08-24
 * Email: abhishek_jhanwar@apple.com
 **/
public class TreeManager implements AlgoManager {

    private static final Logger logger = LogManager.getLogger(TreeManager.class);

    private TreeAlgos treeAlgos;
    private Tree<Integer> rootTree;

    private static void initializeConsoleProxy() {
        System.setOut(createLoggingProxy(System.out, LogManager.getLogger("STDOUT")));
        System.setErr(createLoggingProxy(System.err, LogManager.getLogger("STDERR")));
    }

    private static PrintStream createLoggingProxy(final PrintStream realPrintStream, Logger logger) {
        return new PrintStream(realPrintStream) {
            @Override
            public void print(final String string) {
                realPrintStream.print(string);
                logger.info(string);
            }
        };
    }

    public static void main(String[] args) {
        initializeConsoleProxy();
        logger.debug("Starting Tree Manager");
        TreeManager treeManager = new TreeManager();
        treeManager.init(args);
        treeManager.start();
        treeManager.stop();
        logger.info("Exiting Tree Manager");
    }

    TreeManager() {
        this.treeAlgos = new TreeAlgos();
    }

    @Override
    public boolean start() {
        logger.info("Starting Inorder Traversal");
        treeAlgos.inorder(rootTree);

        logger.info("Starting iterative Inorder Traversal");
        treeAlgos.iterativeInorder(rootTree);

        logger.info("Starting PreOrder Traversal");
        treeAlgos.preorder(rootTree);

        logger.info("Starting iterative PreOrder Traversal");
        treeAlgos.iterativePreorder(rootTree);

        logger.info("Starting PostOrder Traversal");
        treeAlgos.postOrder(rootTree);

        logger.info("Starting iterative PostOrder Traversal");
        treeAlgos.iterativePostorder(rootTree);

        logger.info("Find All paths from root to Leaf with their Sum");
        List<List<Integer>> result = treeAlgos.findAllPathsRootToLeaf(rootTree);
        displayListofList(result);

        logger.info("Find All paths from root to Leaf BST with their Sum " + 20);
        List<List<Integer>> bstResult = treeAlgos.findAllPathsRootToLeafBST(rootTree, 20);
        displayListofList(bstResult);

        logger.info("Find MaxPath Sum any to any nodee");
        List<List<Integer>> maxPathSumList = treeAlgos.findMaxPathSumAnyToAny(rootTree);
        displayListofList(maxPathSumList);

        logger.info("Deleting Node: {}", 12);
        rootTree = treeAlgos.deleteNode(rootTree, 12);
        treeAlgos.displayTree(rootTree);

        logger.info("BFS Traversal");
        treeAlgos.traversalBFS(rootTree);
        return true;
    }

    private void displayListofList(List<List<Integer>> result) {
        if (result == null) {
            logger.error(" list of result null");
        } else {
            result.forEach(res -> {
                logger.info("List -> ");
                displayList(res);
            });
        }
    }

    private void displayList(List<Integer> list)  {
        for (int elem : list) {
            logger.info(elem);
        }
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean init(String[] args) {
        this.rootTree = createTree(args);
        treeAlgos.displayTree(rootTree);
        return true;
    }

    private Tree createTree(String[] args) {
        int numNodes = args.length;
        if (numNodes == 0) {
            throw new IllegalArgumentException("No nodes provided ");
        }
        Tree root = new Tree(Integer.parseInt(args[0]));
        int i=1;
        Queue<Tree> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty() && i<numNodes) {
            Tree node = queue.poll();
            String nodeArg = args[i++];
            if (nodeArg.compareTo("null")!=0) {
                Tree left = new Tree(Integer.parseInt(nodeArg));
                queue.offer(left);
                node.setLeft(left);
            }
            if (i>=numNodes) {
                break;
            }
            nodeArg = args[i++];
            if (nodeArg.compareTo("null") != 0) {
                Tree right = new Tree(Integer.parseInt(nodeArg));
                queue.offer(right);
                node.setRight(right);
            }

        }
        return root;
    }
}
