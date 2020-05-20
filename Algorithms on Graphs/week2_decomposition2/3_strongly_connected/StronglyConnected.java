import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;
// Approach:
// 1. Create a reverseG where all the paths of the edges are reversed
// 2. Do a DFS on the adj graph (G) and store the post-order numbers in an array in increasing order
// 3. Explore the vertices in the post-order array in decreasing order and count the SCCs

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        //write your code here
        // initialize all the arrays as unvisited
        boolean[] visited = new boolean[adj.length];
        Arrays.fill(visited, false);
        int res = 0;
        // reverse adj
        ArrayList<Integer>[] reverseG = reverseAdj(adj);
        // help record the post orders  
        int[] postOrders = new int[adj.length];
        int currInd = 0;
        for(int i = 0; i < adj.length; i++) {
            // explores vertex i, then fills postOrders
            // returns currInd + 1 and stores it to currInd
            currInd = fillPostOrders(adj, i, currInd, postOrders, visited);
        }
        // reset all nodes as un visited for the reverseG
        Arrays.fill(visited, false);
        for(int i = postOrders.length - 1; i > -1; i--) {
            // visit each vertex in reverseG in decreasing postorder
            int vertex = postOrders[i];
            if(!visited[vertex]) {
                exploreReverseG(reverseG, postOrders[i], visited);
                res++;
            }         
        }
        return res;
    }

    // reverse the direction of the graph nodes
    private static ArrayList<Integer>[] reverseAdj(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] reverseG = (ArrayList<Integer>[])new ArrayList[adj.length];
        // initialize all the nodes to be array lists
        for(int i = 0; i < adj.length; i++) {
            reverseG[i] = new ArrayList<Integer>();
        }
        // reverse the process
        for(int i = 0; i < adj.length; i++) {
            ArrayList<Integer> nextNodes = adj[i];
            for(int v : nextNodes) {
                reverseG[v].add(i);
            }
        }
        return reverseG;
    }

    private static int fillPostOrders(ArrayList<Integer>[] adj, int v, int currInd, int[] postOrders, boolean[] visited) {
        if(visited[v]) {
            return currInd;
        }

        ArrayList<Integer> neighbors = adj[v];
        visited[v] = true;
        for(int i = 0; i < neighbors.size(); i++) {
            currInd = fillPostOrders(adj, neighbors.get(i), currInd, postOrders, visited);
        }
        postOrders[currInd] = v;
        return currInd + 1;
    }

    private static void exploreReverseG(ArrayList<Integer>[] reverseG, int v, boolean[] visited) {
        if(visited[v]) {
            return;
        }

        ArrayList<Integer> neighbors = reverseG[v];
        visited[v] = true;

        for(int i = 0; i < neighbors.size(); i++) {
            int nextNode = neighbors.get(i);
            exploreReverseG(reverseG, nextNode, visited);
        }
        return;
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}

