package com.sigruptor.datastructure.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abhishek_jhanwar on 9/8/20
 * Email: abhishek_jhanwar@apple.com
 **/
public class Graph<K, T> {

    private static final Logger logger = LogManager.getLogger(Graph.class);

    private final Map<K, List<Node<K, T>>> graph;
    boolean isDirected;

    public Graph() {
        this(false);
    }

    public Graph(boolean isDirected) {
        this.graph = new HashMap<>();
        this.isDirected = isDirected;
    }

    public void addNode(K nodeId, K dstNodeId, T val) {
        List<Node<K, T>> nodeList = graph.computeIfAbsent(nodeId, k -> new ArrayList<>());
        Node<K, T> node = new Node<>(dstNodeId, val);
        nodeList.add(node);
    }

    public void displayGraph() {
        if (graph == null) {
            logger.debug("Graph is null");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, List<Node<K, T>>> entry : graph.entrySet()) {
            List<Node<K, T>> nodeList = entry.getValue();
            sb.append("Id: ").append(entry.getKey()).append(" -> ");
            for (Node<K, T> node : nodeList) {
                sb.append(node.val).append(", ");
            }
            sb.append("\n");
        }
        logger.info("Graph: {}", sb.toString());
    }

    public int size() {
        return graph.size();
    }

    public Map<K, List<Node<K, T>>> getGraph() {
        return graph;
    }

    public static class Node<K, T> {
        T val;
        K nodeId;
        Node(K nodeId, T val) {
            this.val = val;
            this.nodeId = nodeId;
        }
    }

    public static class GraphNode {
        int val;
        int cost;

        public GraphNode(int val, int cost) {
            this.val = val;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "(" + val + ", " + cost + ")";
        }
    }
}
