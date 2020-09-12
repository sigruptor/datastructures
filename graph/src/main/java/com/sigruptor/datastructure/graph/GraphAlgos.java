package com.sigruptor.datastructure.graph;

import com.sigruptor.datastructure.stack.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author abhishek_jhanwar on 9/8/20
 * <p>
 * 1. DFS and BFS -> (Recursive and Iterative) (Connected Components)
 * 2. Src to all dst cost (Djikstra)
 * 3. All Src to dst cost and route (Floyd Warshall)
 * 4. Detect a loop
 * 5. Topological Sort
 * 6. Minimum Spanning Tree
 * 7.
 **/
public class GraphAlgos {
    private static final Logger logger = LogManager.getLogger(GraphAlgos.class);

    /**
     * Using DFS to iterate the graph and find connected components
     *
     * @param graph -> graph
     * @return List of paths
     */
    public List<List<Integer>> findConnectedComponents(Graph<Integer, Graph.GraphNode> graph, boolean isIterative) {
        if (graph == null || graph.size() == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Boolean> isVisited = new HashMap<>();
        for (Integer nodeId : graph.getGraph().keySet()) {
            if (isVisited.get(nodeId) == null || !isVisited.get(nodeId)) {
                List<Integer> res = new ArrayList<>();
                if (isIterative) {
                    dfsIterative(nodeId, graph, isVisited, res);
                } else {
                    dfs(nodeId, graph, isVisited, res);
                }
                result.add(res);
            }
        }
        return result;
    }

    /**
     * Applications DFS:
     * 1. Connected/Strongly connected Components
     * 2. Topological sort
     * 3. Solving puzzles with one sol - mazes
     *
     * @param srcNode   -> dfs startt node
     * @param graph     -> graph
     * @param isVisited -> map of already visited nodes
     * @param path      -> path followed so far
     */
    private void dfs(int srcNode, Graph<Integer, Graph.GraphNode> graph,
                     Map<Integer, Boolean> isVisited, List<Integer> path) {
        if (isVisited.get(srcNode) != null && isVisited.get(srcNode)) {
            logger.debug("Node already visited {}", srcNode);
            return;
        }
        isVisited.put(srcNode, true);
        path.add(srcNode);
        List<Graph.Node<Integer, Graph.GraphNode>> nodes = graph.getGraph().get(srcNode);
        if (nodes == null) {
            logger.error("No nodes found for nodeId: {}", srcNode);
            return;
        }
        for (Graph.Node<Integer, Graph.GraphNode> node : nodes) {
            if (isVisited.get(node.nodeId) == null || !isVisited.get(node.nodeId)) {
                dfs(node.nodeId, graph, isVisited, path);
            }
        }
    }

    private void dfsIterative(int srcNode, Graph<Integer, Graph.GraphNode> graph,
                              Map<Integer, Boolean> isVisited, List<Integer> path) {

        Stack<Integer> stack = new Stack<>();
        stack.push(srcNode);
        while (!stack.isEmpty()) {
            int sNode = stack.pop();
            if (isVisited.get(sNode) != null && isVisited.get(sNode)) {
                logger.debug("Node already visited {}", sNode);
                continue;
            }
            List<Graph.Node<Integer, Graph.GraphNode>> nodes = graph.getGraph().get(sNode);
            isVisited.put(sNode, true);
            path.add(sNode);
            for (Graph.Node<Integer, Graph.GraphNode> node : nodes) {
                if (isVisited.get(node.nodeId) == null || !isVisited.get(node.nodeId)) {
                    stack.push(node.nodeId);
                }
            }
        }
    }

    /**
     * 1. Shortest path
     * 2. Web crawler
     * 3. MST
     * 4. serialization/deserialization
     *
     * @param graph
     */
    public Map<Integer, List<Integer>> bfsShortestPath(Graph<Integer, Graph.GraphNode> graph, int srcId) {
        if (graph == null || graph.size() == 0) {
            return Collections.emptyMap();
        }
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> path = new ArrayList<>();
        path.add(srcId);
        queue.offer(path);

        Map<Integer, List<Integer>> pathMap = new HashMap<>();
        int cost = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                List<Integer> curDstPath = queue.poll();
                int nodeId = curDstPath.get(curDstPath.size() - 1);
                if (pathMap.containsKey(nodeId)) {
                    logger.debug("Node Already visited: {}", nodeId);
                    continue;
                }
                curDstPath.add(cost);
                pathMap.put(nodeId, curDstPath);
                List<Graph.Node<Integer, Graph.GraphNode>> nodes = graph.getGraph().get(nodeId);
                if (nodes == null) {
                    continue;
                }
                for (Graph.Node<Integer, Graph.GraphNode> node : nodes) {
                    List<Integer> curNodePath = new ArrayList<>(curDstPath.subList(0, curDstPath.size() - 1));
                    curNodePath.add(node.nodeId);
                    queue.offer(curNodePath);
                }
            }
            cost++;
        }
        return pathMap;
    }

    /**
     * Shortest path src to dst, cost of each path is given
     */
    public List<Integer> shortestPathCostSrcToDst(Graph<Integer, Graph.GraphNode> graph, int srcId, int dstId) {
        if (graph == null || graph.size() == 0) {
            return Collections.emptyList();
        }

        // cost array cost[i]-> cost to reach node i from srcNode
        int[] cost = new int[graph.size() + 2];
        Arrays.fill(cost, Integer.MAX_VALUE);

        List<Integer> path = new ArrayList<>();
        cost[srcId] = 0;
        path.add(srcId);
        path = shortestPathCostSrcToDstDfs(graph, srcId, dstId, cost, path);
        if (path != null) {
            path.add(cost[dstId]);
        }
        return path;
    }

    private List<Integer> shortestPathCostSrcToDstDfs(Graph<Integer, Graph.GraphNode> graph,
                                                      int srcNode, int dstNode, int[] cost, List<Integer> path) {
        if (srcNode == dstNode) {
            return path;
        }
        List<Graph.Node<Integer, Graph.GraphNode>> nodes = graph.getGraph().get(srcNode);
        if (nodes == null) {
            logger.error("No nodes found for nodeId: {}", srcNode);
            path.add(Integer.MAX_VALUE);
            return path;
        }

        int minCost = Integer.MAX_VALUE;
        List<Integer> chosenPath = null;
        for (Graph.Node<Integer, Graph.GraphNode> node : nodes) {
            // cost of each path is assumed to be > 0
            if (cost[node.nodeId] > cost[srcNode] + node.val.cost && node.val.cost > 0) {
                cost[node.nodeId] = cost[srcNode] + node.val.cost;
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node.nodeId);
                if (node.nodeId == dstNode) {
                    return newPath;
                }
                List<Integer> pathCost = shortestPathCostSrcToDstDfs(graph, node.nodeId, dstNode, cost, newPath);
                if (cost[dstNode] < minCost) {
                    minCost = cost[dstNode];
                    chosenPath = pathCost;
                }
            }
        }
        return chosenPath;
    }

    /**
     * Find all src to all dst path and cost
     *
     * @param graph
     */
    public void allSrcToDstPath(Graph<Integer, Graph.GraphNode> graph) {
        if (graph == null || graph.size() == 0) {
            return;
        }
        int[][] cost = new int[graph.size() + 1][graph.size() + 1];
        int[][] path = new int[graph.size() + 1][graph.size() + 1];

        for (int i = 0; i < graph.size()+1; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
            Arrays.fill(path[i], -1);
        }

        // iterate the graph to fill in the edges
        for (int i=0;i<graph.size();i++) {
            List<Graph.Node<Integer, Graph.GraphNode>> nodes = graph.getGraph().get(i);
            if (nodes != null) {
                for (Graph.Node<Integer, Graph.GraphNode> node : nodes) {
                    cost[i][node.nodeId] = node.val.cost;
                    path[i][node.nodeId] = i;
                }
            }
        }
        for (int k = 0; k < graph.size(); k++) {
            for (int i = 0; i < graph.size(); i++) {
                for (int j = 0; j < graph.size(); j++) {
                    if (cost[i][k] != Integer.MAX_VALUE && cost[k][j] != Integer.MAX_VALUE
                            && cost[i][j] > cost[i][k]+cost[k][j]) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }

        printAllPathsWithCosts(cost, path);
    }

    private void printAllPathsWithCosts(int[][] cost, int[][] path) {
        for (int i=0;i<cost.length;i++) {
            for (int j=0;j<cost.length;j++) {
                if (i!=j && path[i][j]!=-1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Path from src: " + i + " dst: "+j + " with Cost: " + cost[i][j]+ " and Path -> (");
                    sb.append(i).append(", ");
                    printPath(path, i, j, sb);
                    sb.append(j).append(")");
                    sb.append("\n");
                    logger.info(sb.toString());
                }
            }
        }
    }

    private void printPath(int[][] path, int src, int dst, StringBuilder sb) {
        if (path[src][dst] == src || path[src][dst]==-1) {
            return;
        }
        printPath(path, src, path[src][dst], sb);
        sb.append(path[src][dst] + ", ");
    }
}
