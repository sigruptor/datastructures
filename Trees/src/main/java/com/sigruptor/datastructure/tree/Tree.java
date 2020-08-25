package com.sigruptor.datastructure.tree;

/**
 * @author abhishek_jhanwar on 2020-08-24
 *
 **/
public class Tree<T> {
    private T val;
    private Tree left;
    private Tree right;

    Tree(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

}
