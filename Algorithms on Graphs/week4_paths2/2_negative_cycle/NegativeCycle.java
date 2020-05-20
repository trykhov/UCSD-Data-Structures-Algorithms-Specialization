import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class NegativeCycle {

    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        // Use Bellman Ford to find a negative cycle
        // write your code here
        double[] distance = new double[adj.length];
        // initialize all the distances to be Infinity (except the first node)
        Arrays.fill(distance, Math.pow(10, 3));
        distance[0] = 0;
        // while Bellman-Ford updates each node |V| - 1 times, finding a cycle requires a |V|th iteration 
        for(int i = 0; i < adj.length; i++) {
            for(int j = 0; j < adj.length; j++) {
                ArrayList<Integer> neighbors = adj[j];
                for(int x = 0; x < neighbors.size(); x++) {
                    int node = neighbors.get(x);
                    double prior = distance[node];
                    distance[node] = Math.min(distance[node], distance[j] + cost[j].get(x));
                    if((i == adj.length - 1) && (distance[node] < prior)) {
                        return 1;
                    } 
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

