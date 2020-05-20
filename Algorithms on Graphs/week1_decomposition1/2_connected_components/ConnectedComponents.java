import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class ConnectedComponents {
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int result = 0;
        boolean[] visited = new boolean[adj.length];
        // write your code here
        // have each element have its own node to mark unvisited
        Arrays.fill(visited, false);
        for(int i = 0; i < adj.length; i++) {
            if(!visited[i]) {
                // do a depth first search on non-visited nodes
                dfs(adj, i, visited);
                // count only non-visited nodes
                result++;
            }
        }
        return result;
    }

    private static void dfs(ArrayList<Integer>[] adj, int x, boolean[] visited) {
        // if the list has been visited
        if(visited[x]) {
            return;
        } 
        ArrayList<Integer> neighbors = adj[x];
        // mark as visited
        visited[x] = true;
        // the last element of each neighbor is the node itself (don't count it)
        for(int i = 0; i < neighbors.size(); i++) {
            int neighborNode = neighbors.get(i);
            if(!visited[neighborNode]) {
                dfs(adj, neighbors.get(i), visited);
            }
        }
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
        System.out.println(numberOfComponents(adj));
    }
}

