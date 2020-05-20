import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        // have a colors array to mark each node as visited
        String[] colored = new String[adj.length];
        // initialize the queue
        Queue<Integer> queue = new LinkedList<Integer>();
        // add the first element in adj to the queue
        for(int j = 0; j < adj.length; j++) {
            int numNeighbors = adj[j].size();
            if(colored[j] == null) {
                // let "b" and "w" represent opposing colors
                colored[j] = "b";
                queue.add(j);
            }
            while(queue.size() > 0) {
                int node = queue.remove();
                ArrayList<Integer> neighbors = adj[node];
                String color = colored[node];
                for(int i = 0; i  < neighbors.size(); i++) {
                    int neighborNode = neighbors.get(i);
                    // if the node hasn't been colored
                    if(colored[neighborNode] == null) {
                        // add to the queue
                        queue.add(neighborNode);
                        // color the other adj nodes
                        if(color == "b") {
                            colored[neighborNode] = "w";
                        } else {
                            colored[neighborNode] = "b";
                        }
                    // if the node is already colored
                    } else {
                        // see if the nodes are the same color
                        if(colored[neighborNode] == color) {
                            return 0;
                        }
                    }
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}

