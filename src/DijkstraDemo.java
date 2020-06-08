import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DijkstraDemo {
    public static JFrame frame;

    private static Node start;
    private static Node end;

    public static void main(String[] args) {
        Graph<Node, DefaultEdge> graph = createGraph();
        List<Node> nodeList = new ArrayList<Node>(graph.vertexSet());

        frame = new JFrame() {
            public void paint(Graphics g) {
                Set<DefaultEdge> pathEdges = new HashSet<>(calculatePath(graph).getEdgeList());

                for (int i = 0; i < nodeList.size(); i++) {
                    for (int j = i+1; j < nodeList.size(); j++) {
                        // For every pair of nodes,

                        Node from = nodeList.get(i);
                        Node to = nodeList.get(j);

                        // if there's an edge between them,
                        if (!graph.containsEdge(from, to)) {
                            continue;
                        }

                        // draw the edge between the two nodes with a color based on if it's part of the path,
                        DefaultEdge edge = graph.getEdge(from, to);
                        Color edgeColor = (pathEdges.contains(edge)) ? Color.MAGENTA : Color.BLACK;
                        g.setColor(edgeColor);
                        g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());

                        // and label the edge with its weight.
                        g.setColor(Color.BLACK);
                        int weight = (int) graph.getEdgeWeight(edge);
                        g.drawString(""+weight, (from.getX()+to.getX())/2, (from.getY()+to.getY())/2);
                    }

                    nodeList.get(i).paint(g);
                }
            }
        };

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(640, 480);

        frame.addMouseListener(new DijkstraMouseListener(graph.vertexSet()));
    }

    /**
     * Create a weighted graph for demonstration purposes. It's hardcoded because this is a demo of the graphing
     * library, not my ability to make something not hardcoded. Making a demo needlessly complicated makes it harder
     * to understand. Excessive abstraction in a demo makes it harder to follow.
     */
    private static Graph<Node, DefaultEdge> createGraph() {
        Graph<Node, DefaultEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultEdge.class);

        Node[] nodes = {
                new Node(300, 400, "A"),
                new Node(260, 200, "B"),
                new Node(100, 120, "C"),
                new Node(150, 500, "D"),
                new Node(500, 100, "E"),
                new Node(280, 100, "F"),
        };

        // default start and end. They have to be initialized, so this is that.
        start = nodes[0];
        end = nodes[1];

        start.setBackground(Color.ORANGE);
        end.setBackground(Color.BLUE);

        double[][] weights = {
                {0, 1, -1, 2, 10, -1},
                {0, 0, 3, 2, 3, -1},
                {0, 0, 0, 20, -1, 3},
                {0, 0, 0, 0, -1, -1},
                {0, 0, 0, 0, 0, 5},
        };

        for (Node node: nodes) {
            graph.addVertex(node);
        }

        for (int i = 0; i < nodes.length; i++) {
            for (int j = i + 1; j < nodes.length; j++) {
                if (weights[i][j] < 0) {
                    continue;
                }
                DefaultEdge edge = graph.addEdge(nodes[i], nodes[j]);
                graph.setEdgeWeight(edge, weights[i][j]);
            }
        }

        return graph;
    }

    public static void updateStart(Node newStart) {
        start.setBackground(Color.BLACK);
        start = newStart;
        start.setBackground(Color.ORANGE);
        frame.repaint();
    }

    public static void updateEnd(Node newStart) {
        end.setBackground(Color.BLACK);
        end = newStart;
        end.setBackground(Color.BLUE);
        frame.repaint();
    }

    /**
     * This actually computes the path from the start to the end. These two lines. That's it. This is the entire point
     * of this demo. Two lines.
     */
    private static GraphPath<Node, DefaultEdge> calculatePath(Graph<Node, DefaultEdge> graph) {
        DijkstraShortestPath<Node, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPath(start, end);
    }

    /**
     * Utility class to represent vertices that have a position and a label.
     */
    public static class Node extends Component {
        private String label;
        public static final int RADUIS = 20;

        public Node(int x, int y, String label) {
            this.label = label;
            setLocation(x, y);
            setBackground(Color.BLACK);
        }

        public void paint(Graphics g) {
            g.setColor(getBackground());
            g.fillOval(getX()-RADUIS, getY()-RADUIS, 2*RADUIS, 2*RADUIS);
            g.setColor(Color.WHITE);
            g.drawString(label, getX() + getWidth()/2-3, getY() + getHeight()/2+5);
        }
    }
}
