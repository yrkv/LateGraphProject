import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class WordDemo {

    public static void main(String[] args) throws IOException {
        // I wasn't sure if it would be better to break each task into a separate method (with the println calls),
        // but eventually settled on doing it like this because it more directly reflects the output structure and keeps
        // the println calls centralized to the main method, with all the actual computation being done in methods.

        // Since this is just a demo to show the library being used, it would be unusual for it to be structured exactly
        // like a normal project. Excessive abstraction in a demo makes it harder to follow.

        System.out.println("Creating graph...");
        Graph<String, DefaultEdge> graph = createGraph("dictionary.txt");
        System.out.println("Created graph");

        System.out.println("---");
        System.out.println("All words reachable from the word 'chair'");
        ConnectivityInspector<String, DefaultEdge> connectivity = new ConnectivityInspector<>(graph);
        System.out.println(connectivity.connectedSetOf("chair"));

        System.out.println("---");
        System.out.println("Shortest path between 'graph' and 'trees'");
        BFSShortestPath<String, DefaultEdge> bfs = new BFSShortestPath<>(graph);
        GraphPath<String, DefaultEdge> path = bfs.getPath("graph", "trees");
        System.out.println(path.getVertexList());

        System.out.println("---");
        System.out.println("Furthest word from 'graph'");
        ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> paths = bfs.getPaths("graph");
        String maxWord = furthestWord(graph, paths);
        System.out.println(maxWord);
        System.out.println(paths.getPath(maxWord).getVertexList());

        System.out.println("---");
        List<Set<String>> connectedSets = connectivity.connectedSets();
        System.out.printf("Number of connected sets: %d\n", connectedSets.size());
        System.out.println("Number of connected sets of each size:");
        Map<Integer, Integer> counter = new HashMap<>();
        for (Set<String> set : connectedSets) {
            counter.put(set.size(), counter.getOrDefault(set.size(), 0) + 1);
        }
        System.out.println(counter);

    }

    /**
     * Create a graph containing each word in a dictionary as a node, with there being an edge between words if they
     * are exactly one character replacement different.
     * @param dictPath The path of a text file containing a list of words, one per line
     */
    private static Graph<String, DefaultEdge> createGraph(String dictPath) throws FileNotFoundException {
        Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);


        File dict = new File(dictPath);
        Scanner scanner = new Scanner(dict);

        while (scanner.hasNextLine()) {
            graph.addVertex(scanner.nextLine().toLowerCase());
        }

        initEdges(graph);

        return graph;

    }

    /**
     * Add all the necessary edges between words. For every word, try altering it in every possible way, see which are
     * vertices already in the graph, and attach them with edges.
     */
    private static void initEdges(Graph<String, DefaultEdge> graph) {
        for (String word: graph.vertexSet()) {
            // for every character, try switching it out for everything and check if results are also real words
            for (int i = 0; i < word.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    String altered = word.substring(0, i) + c + word.substring(i+1);
                    if (graph.containsVertex(altered) && !graph.containsEdge(word, altered)) {
                        graph.addEdge(word, altered);
                    }
                }
            }
        }
    }

    /**
     * Calculate the furthest word based on the already defined paths.
     */
    private static String furthestWord(Graph<String, DefaultEdge> graph,
                                       ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> paths) {
        double maxWeight = 0;
        String maxWord = "";
        for (String sink : graph.vertexSet()) {
            double weight = paths.getWeight(sink);
            if (weight > maxWeight && weight < Double.MAX_VALUE) {
                maxWord = sink;
                maxWeight = weight;
            }
        }
        return maxWord;
    }


}
