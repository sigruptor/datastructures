package com.sigruptor.datastructure.tree;

import com.sigruptor.datastructure.stack.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author abhishek_jhanwar on 2020-08-24
 * <p>
 * 1. InOrder/PreOrder/PostOrder -> Recursive
 * 2. InOrder/PreOrder/PostOrder -> Iterative
 * 3. Root to leaf path sum along with the paths - BST and Binary Tree
 * 4. Any node to any node path and the sum - BST and Binary Tree
 * 5. DFS and BFS
 * 6. Create BST from PreOrder and Inorder traversal
 * 7. Create BST from PostOrder and Inorder traversal
 * 8. Successor and Predecessor
 * 9. Deletion in a BST/ Self Balancing Tree
 **/
public class TreeAlgos {
    private static final Logger logger = LogManager.getLogger(TreeAlgos.class);

    public void displayTree(Tree root) {
        if (root == null) {
            logger.debug("root is null");
        }
        logger.info(root.getVal());
        if (root.getLeft() != null) {
            displayTree(root.getLeft());
        }
        if (root.getRight() != null) {
            displayTree(root.getRight());
        }
    }


    /**
     * left child first, root and then right child
     *
     * @param root
     */
    public void inorder(Tree root) {
        if (root == null) {
            logger.debug("[Inorder] Root is null, returning");
            return;
        }
        if (root.getLeft() != null) {
            inorder(root.getLeft());
        }
        logger.info(root.getVal());
        if (root.getRight() != null) {
            inorder(root.getRight());
        }
    }

    public void iterativeInorder(Tree root) {
        if (root == null) {
            logger.debug("[Iterative] Root is null, returning");
            return;
        }
        Stack<Tree> stack = new Stack();
        Tree node = root;

        while (node != null || !stack.isEmpty()) {
            if (node == null) {
                node = stack.pop();
            } else if (node.getLeft() != null) {
                stack.push(node);
                node = node.getLeft();
                continue;
            }
            logger.info(node.getVal());
            node = node.getRight();
        }
    }

    public void preorder(Tree root) {
        if (root == null) {
            logger.debug("Root is null, returning");
            return;
        }
        logger.info(root.getVal());
        if (root.getLeft() != null) {
            preorder(root.getLeft());
        }
        if (root.getRight() != null) {
            preorder(root.getRight());
        }
    }

    public void iterativePreorder(Tree root) {
        if (root == null) {
            logger.debug("Root is null, returning");
            return;
        }
        Stack<Tree> stack = new Stack<>();
        Tree node = root;
        while (node != null || !stack.isEmpty()) {
            if (node == null) {
                node = stack.pop();
            }
            logger.info(node.getVal());
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            node = node.getLeft();

        }
    }

    public void postOrder(Tree root) {
        if (root == null) {
            logger.debug("Root is null, returning");
            return;
        }
        if (root.getLeft() != null) {
            postOrder(root.getLeft());
        }
        if (root.getRight() != null) {
            postOrder(root.getRight());
        }
        logger.info(root.getVal());
    }

    public void iterativePostorder(Tree root) {
        if (root == null) {
            logger.debug("Root is null, returning");
            return;
        }
        Stack<Tree> stack = new Stack();
        Tree prev = null;
        Tree node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                node = stack.seek();
                if (node.getRight() == null || node.getRight() == prev) {
                    logger.info(node.getVal());
                    prev = node;
                    stack.pop();
                    node = null;
                } else {
                    node = node.getRight();
                }
            }
        }
    }

    public List<List<Integer>> findAllPathsRootToLeaf(Tree root) {
        List<List<Integer>> result = new ArrayList();
        if (root == null) {
            return result;
        }
        List<Integer> curList = new ArrayList();
        findAllPathsRootToLeaf(root, curList, result, 0);
        return result;
    }

    public void findAllPathsRootToLeaf(Tree root, List<Integer> curList, List<List<Integer>> result, int curSum) {
        if (root == null) {
            return;
        }
        curSum += (Integer) root.getVal();
        curList.add((Integer) root.getVal());
        if (root.getLeft() == null && root.getRight() == null) {
            curList.add(curSum);
            result.add(curList);
            return;
        }
        if (root.getRight() != null) {
            findAllPathsRootToLeaf(root.getRight(), new ArrayList<>(curList), result, curSum);
        }
        if (root.getLeft() != null) {
            findAllPathsRootToLeaf(root.getLeft(), curList, result, curSum);
        }
    }

    public List<List<Integer>> findAllPathsRootToLeafBST(Tree root, int K) {
        List<List<Integer>> result = new ArrayList();
        if (root == null) {
            return result;
        }
        List<Integer> curList = new ArrayList();

        findAllPathsRootToLeafBST(root, curList, result, 0, K);
        return result;
    }

    private void findAllPathsRootToLeafBST(Tree root, List<Integer> curList,
                                           List<List<Integer>> result, int curSum, int K) {
        if (root == null) {
            return;
        }
        curSum += (Integer) root.getVal();
        curList.add((Integer) root.getVal());
        if (root.getLeft() == null && root.getRight() == null) {
            if (curSum == K) {
                result.add(curList);
            }
        }
        if (root.getRight() != null) {
            if ((int) root.getVal() > 0 && curSum < K) {
                findAllPathsRootToLeafBST(root.getRight(), new ArrayList<>(curList), result, curSum, K);
            }
        }

        if (root.getLeft() != null) {
            findAllPathsRootToLeafBST(root.getLeft(), curList, result, curSum, K);
        }
    }

    public List<List<Integer>> findMaxPathSumAnyToAny(Tree root) {
        int[] maxValue = {Integer.MIN_VALUE};
        List<List<Integer>> result = new ArrayList();
        List<Integer> curList = new ArrayList();
        findMaxPathSumAnyToAny(root, curList, result, maxValue);
        logger.info("MaxSum is : " + maxValue[0]);
        return result;
    }

    private int findMaxPathSumAnyToAny(Tree root, List<Integer> curList, List<List<Integer>> results, int[] maxValue) {
        if (root == null) {
            return 0;
        }
        int left = findMaxPathSumAnyToAny(root.getLeft(), curList, results, maxValue);
        List<Integer> rightList = new ArrayList<>(curList);
        int right = findMaxPathSumAnyToAny(root.getRight(), rightList, results, maxValue);
        int all = left + right + (Integer) root.getVal();
        left += (Integer) root.getVal();
        right += (Integer) root.getVal();

        maxValue[0] = Math.max(maxValue[0], all);
        left = Math.max(left, (Integer) root.getVal());
        right = Math.max(right, (Integer) root.getVal());
        int sum = Math.max(left, right);
        maxValue[0] = Math.max(maxValue[0], sum);

        return sum;
    }

    public Tree successor(Tree node) {
        if (node == null || node.getRight() == null) {
            return null;
        }
        Tree right = node.getRight();
        while (right.getLeft()!=null) {
            right = right.getLeft();
        }
        return right;
    }

    public Tree predecessor(Tree root, int K) {
        if (root == null) {
            return null;
        }
        Tree pred = null;
        while (root != null) {
            if ((Integer)root.getVal() == K) {
                if (root.getLeft() != null) {
                    pred = root.getLeft();
                    while (pred.getRight()!=null) {
                        pred = pred.getRight();
                    }
                    break;
                }
            } else if ((Integer)root.getVal() < K){
                pred = root;
                root = root.getRight();
            } else {
                root = root.getLeft();
            }
        }
        return pred;
    }

    public Tree deleteNode(Tree root, int K) {
        if (root==null) {
            return null;
        }
        if ((Integer)root.getVal() == K) {
            if (root.getLeft() == null && root.getRight() == null) {
                // no children
                return null;
            } else if (root.getRight()!=null) {
                Tree successor = successor(root);
                root.setVal(successor.getVal());
                root.setRight(deleteNode(root.getRight(), (Integer) successor.getVal()));
            } else {
                return root.getLeft();
            }
        } else if ((Integer)root.getVal() > K) {
            root.setLeft(deleteNode(root.getLeft(), K));
        } else {
            root.setRight(deleteNode(root.getRight(), K));
        }
        return root;
    }

    public void traversalBFS(Tree root) {
        if (root == null) {
            logger.debug("Root is null");
            return;
        }
        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i=0;i<qSize;i++) {
                Tree node = queue.poll();
                logger.info("level: {}, values: {}", level, node.getVal());
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
            level++;
        }
    }
}
