package com.sigruptor.datastructure.graph;

import com.sigruptor.datastructure.utils.AlgoManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author abhishek_jhanwar on 9/8/20
 * Email: abhishek_jhanwar@apple.com
 **/
public class GraphManager implements AlgoManager {

    private static final Logger logger = LogManager.getLogger(GraphManager.class);

    private Graph graph;
    private GraphAlgos graphAlgos;

    @Override
    public boolean start() {
        logger.debug("Find connected components");
        List<List<Integer>> paths = graphAlgos.findConnectedComponents(graph, false);
        displayList(paths);

        paths = graphAlgos.findConnectedComponents(graph, true);
        displayList(paths);

        /** -------------------BFS shortest path------------------*/
        Map<Integer, List<Integer>> shortestPathMap = graphAlgos.bfsShortestPath(graph, 6);
        StringBuilder sb = new StringBuilder("Path from src - 6 to \n");
        for (Map.Entry<Integer, List<Integer>> entry : shortestPathMap.entrySet()) {
            int dstId = entry.getKey();
            sb.append("dst: " + dstId).append(" -> path: ");
            List<Integer> path = entry.getValue();
            for (int i=0;i<path.size()-1;i++) {
                sb.append(path.get(i)+", ");
            }
            if (path.size()>=2) {
                sb.append(" Cost -> " + path.get(path.size()-1));
            }
            sb.append("\n");
        }
        logger.info(sb.toString());

        /** -------------------Shortest path src to dst------------------*/
        logger.info("Shortest path from src to dst");
        List<Integer> path = graphAlgos.shortestPathCostSrcToDst(graph, 0, 3);
        if (path == null) {
            logger.error("Could not find shortest path from 0 to 3");
        } else {
            sb = new StringBuilder("Shortest path from 0 to 3 \n");
            for (int i=0;i<path.size()-1;i++) {
                sb.append(path.get(i)).append(", ");
            }
            if (path.size()>1) {
                sb.append(" TotalCost: " + path.get(path.size()-1)).append("\n");
            }
            logger.info(sb.toString());
        }

        /** -------------------All Src Dst Paths------------------*/
        logger.info("All Src to dst paths");
        graphAlgos.allSrcToDstPath(graph);
        return false;
    }

    private void displayList(List<List<Integer>> paths) {
        if (paths == null) {
            logger.info("No paths found");
            return;
        }
        StringBuilder sb = new StringBuilder();
        paths.forEach(list -> {
            sb.append("path: ");
            for (int node: list) {
                sb.append(node).append(", ");
            }
            sb.append("\n");
        });
        logger.info("Paths: {}", sb.toString());
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean init(String[] args) {
        this.graphAlgos = new GraphAlgos();
        this.graph = new Graph<Integer, Graph.GraphNode>();
        if (args.length == 0 || args.length%3!=0) {
            throw new IllegalArgumentException("Args not provided, cant create graph, " +
                    "Or args must be form (nodeId, dstNode, cost");
        }
        for (int i=0;i<args.length;i+=3) {
            int srcNodeId = Integer.parseInt(args[i]);
            int dstNodeId = Integer.parseInt(args[i+1]);
            int cost = Integer.parseInt(args[i+2]);
            graph.addNode(srcNodeId, dstNodeId,
                    new Graph.GraphNode(dstNodeId, Integer.parseInt(args[i+2])));
            graph.addNode(dstNodeId, srcNodeId,
                    new Graph.GraphNode(srcNodeId, Integer.parseInt(args[i+2])));
        }
        graph.displayGraph();
        return true;
    }

    public static void main(String[] args) {
        logger.debug("Starting Graph Manager");
        GraphManager graphManager = new GraphManager();
        graphManager.init(args);
        graphManager.start();
        graphManager.stop();
        logger.info("Exiting Graph Manager");
    }

}
