import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Toposort {
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        // mark all the nodes as unvisited
        boolean[] visited = new boolean[adj.length];
        Arrays.fill(visited, false);
        // initialize the order list
        ArrayList<Integer> order = new ArrayList<Integer>();
        //write your code here
        for(int i = 0; i < adj.length; i++) {
            if(!visited[i]) {
                dfs(adj, visited, order, i);
            }
        }
        // reverse the order
        ArrayList<Integer> finalOrder = new ArrayList<Integer>();
        for(int i = 0; i < order.size(); i++) {
            int index = order.size() - i - 1;
            int node = order.get(index);
            finalOrder.add(node);
        }
        return finalOrder;
    }

    private static void dfs(ArrayList<Integer>[] adj, boolean[] visited, ArrayList<Integer> order, int s) {
        //write your code here
        if(visited[s]) {
            return;
        }

        ArrayList<Integer> neighbors = adj[s];
        // mark as visited
        visited[s] = true;
        for(int i = 0; i < neighbors.size(); i++) {
            int node = neighbors.get(i);
            // if the node hasn't been visited
            if(!visited[node]) {
                dfs(adj, visited, order, node);
            }
        }
        order.add(s);
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
        ArrayList<Integer> order = toposort(adj);
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
        System.out.println();
    }
}

