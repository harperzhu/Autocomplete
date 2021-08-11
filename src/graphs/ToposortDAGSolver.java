package graphs;

import minpq.DoubleMapMinPQ;
import minpq.ExtrinsicMinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        ExtrinsicMinPQ<V> pq = new DoubleMapMinPQ<>();
        pq.add(start, 0.0);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        while (!pq.isEmpty()) {
            V from = pq.removeMin();
            for (Edge<V> e : graph.neighbors(from)) {
                V to = e.to;
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(from) + e.weight;
                if (newDist < oldDist) {
                    edgeTo.put(to, e);
                    distTo.put(to, newDist);
                    if (pq.contains(to)) {
                        pq.changePriority(to, newDist);
                    } else {
                        pq.add(to, newDist);
                    }
                }
            }
        }
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        //if the current node is visited
        //find its destination
        //if it is not visited, use recursion to add it into the visited set

        if(visited.contains(start)){
            for(V dest: result){
                if(!visited.contains(result)){
                    visited.add(dest);
                    dfsPostOrder(graph, dest, visited, result);
                    edgeTo.put(start,new Edge(start,dest,f.apply()));
                }

                while(!visited.contains(graph.neighbors(start))){
                    for (Edge<V>: edgeTo.get(start)){
                        double oldDist = distTo.get(dest);
                        double newDist = distTo.get(start) + edgeTo.get(start).weight;
                        if(newDist < oldDist){
                            distTo.put(start,newDist);
                            edgeTo.put(start,new Edge<V>(start,dest,f.apply()));
                        }


                    }
                }
            }
        }

        // Collections.reverse the list.


        //For each node in reverse DFS post-order, "relax" the edge.
        // If the new distance to the neighboring node using the given edge is better than the best-known distTo
        // the neighboring node, update distTo and edgeTo accordingly.
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
