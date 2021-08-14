package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.ShortestPathSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Generative adjacency list graph single-source {@link ShortestPathSolver} implementation of the {@link SeamFinder}
 * interface.
 *
 * @see Graph
 * @see ShortestPathSolver
 * @see SeamFinder
 * @see SeamCarver
 */
public class GenerativeSeamFinder implements SeamFinder {
    /**
     * The constructor for the {@link ShortestPathSolver} implementation.
     */
    private final ShortestPathSolver.Constructor<Node> sps;

    /**
     * Constructs an instance with the given {@link ShortestPathSolver} implementation.
     *
     * @param sps the {@link ShortestPathSolver} implementation.
     */
    public GenerativeSeamFinder(ShortestPathSolver.Constructor<Node> sps) {
        this.sps = sps;
    }

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        PixelGraph graph = new PixelGraph(picture, f);
        List<Node> seam = sps.run(graph, graph.source).solution(graph.sink);
        seam = seam.subList(1, seam.size() - 1); // Skip the source and sink nodes
        List<Integer> result = new ArrayList<>(seam.size());
        for (Node pixel : seam) {
            result.add(((PixelGraph.Pixel) pixel).y);
        }
        return result;
    }

    /**
     * Generative adjacency list graph of {@link Pixel} vertices and {@link EnergyFunction}-weighted edges. Rather than
     * materialize all vertices and edges upfront in the constructor, generates vertices and edges as needed when
     * {@link #neighbors(Node)} is called by a client.
     *
     * @see Pixel
     * @see EnergyFunction
     */
    private static class PixelGraph implements Graph<Node> {
        /**
         * The {@link Picture} for {@link #neighbors(Node)}.
         */
        private final Picture picture;
        /**
         * The {@link EnergyFunction} for {@link #neighbors(Node)}.
         */
        private final EnergyFunction f;

        /**
         * Constructs a generative adjacency list graph. All work is deferred to implementations of
         * {@link Node#neighbors(Picture, EnergyFunction)}.
         *
         * @param picture the input picture.
         * @param f       the input energy function.
         */
        private PixelGraph(Picture picture, EnergyFunction f) {
            this.picture = picture;
            this.f = f;
        }

        /**
         * Source {@link Node} for the adjacency list graph.
         */
        private final Node source = new Node() {
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                //create a for loop to loop through the entire leftmost column
                List<Pixel> list = new ArrayList<>();
                List<Edge<Node>> edgeList = new ArrayList<>();
                for(int i=0;i<picture.height();i++){
                    list.add(new Pixel(0,i));
                    //create edges between that me with neighbor
                    int xCoordinate = list.get(i).x;
                    int yCoordinate = list.get(i).y;
                    edgeList.add(new Edge<>(this, list.get(i),f.apply(picture,xCoordinate,yCoordinate)));
                }

                //TODO: order it with shortest path
                //NOT IMPLEMENTED YET
//                for(int i=0; i<edgeList.size();i++){
//                    Collections.sort(edgeList.get(i).weight);
//                }
                return edgeList;
                };
            };

        /**
         * Sink {@link Node} for the adjacency list graph.
         */
        private final Node sink = new Node() {
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                //sink should grab the rightmost neighbor, which return the empty list in this case
                return List.of();// Sink has no neighbors.
            }
        };


        @Override
        public List<Edge<Node>> neighbors(Node node) {
            //call the neighbor function in pixel class
            //if a regular node, then would be able to grab its neighbor
            return node.neighbors(picture, f);
        }


        /**
         * A pixel in the {@link PixelGraph} representation of the {@link Picture} with {@link EnergyFunction}-weighted
         * edges to neighbors.
         *
         * @see PixelGraph
         * @see Picture
         * @see EnergyFunction
         */
        public class Pixel implements Node {
            private final int x;
            private final int y;
            private final List<Edge<Node>> neighbors;


            /**
             * Constructs a pixel representing the (<i>x</i>, <i>y</i>) indices in the picture.
             *
             * @param x horizontal index into the picture.
             * @param y vertical index into the picture.
             */
            public Pixel(int x, int y) {
                this.x = x;
                this.y = y;
                this.neighbors = new ArrayList<>(3);
            }

            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {

                List newYArray = new ArrayList(3);
                if(x < picture.width()-1) {
                for(int i=y-1;i<y+2;i++) {
                    if(i>= 0 && i<picture.height()) {
                        for(int j=0;j<3;j++) {
                            //create pixel for each right top, middle, bottom
                            newYArray.add(new Pixel(x, i));
                            //add in new edge
                            neighbors.add(new Edge(this, newYArray.get(j), f.apply(picture,x, i)));
                        }

                    }
                    }
                }else if(x == picture.width() -1){
                    neighbors.add(new Edge<>(this,sink,0));
                }

                return neighbors;
            }

            @Override
            public String toString() {
                return "(" + x + ", " + y + ")";
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                } else if (!(o instanceof Pixel)) {
                    return false;
                }
                Pixel other = (Pixel) o;
                return this.x == other.x && this.y == other.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }
    }
}