import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Acyclicity {
    private static int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
        // initialize visited array
        String[] visited = new String[adj.length];
        for(int i = 0; i < adj.length; i++) {
            // v is for visited
            if(visited[i] != "v") {
                boolean foundCycle = dfsDetectCycle(adj, i, visited);
                // if dfs returns cycle, return 1
                if(foundCycle) {
                    return 1;
                }
            }
        }
        return 0;
    }

    // s for start
    // t for end
    private static boolean dfsDetectCycle(ArrayList<Integer>[] adj, int s, String[] visited) {
        // if it has no arrows going out, it's a sink
        if(adj[s].size() == 0) {
            return false;
        }

        // if the node is currently being explored (not visited), then it's a cycle
        if(visited[s] == "e") {
            return true;
        }

        ArrayList<Integer> neighbors = adj[s];
        // mark the node as being explored
        visited[s] = "e";
        for(int i = 0; i < neighbors.size(); i++) {
            int index = neighbors.get(i);
            // go down rabbit hole
            if(visited[index] != "v") {
                boolean foundCycle = dfsDetectCycle(adj, index, visited);
                if(foundCycle) {
                    return true;
                }
                visited[index] = "v";
            }
        }
        // close by marking as visited
        visited[s] = "v";
        // no cycle
        return false;
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
        }
        System.out.println(acyclic(adj));
    }
}

