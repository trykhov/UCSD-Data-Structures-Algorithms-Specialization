import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
        // initialize and mark all nodes as not visited
        boolean[] visited = new boolean[adj.length];
        Arrays.fill(visited, false);
        // initialize queue
        Queue<Integer> queue = new LinkedList<Integer>();
        // have another queue to keep track of the distance from one queue to another
        Queue<Integer> distanceTracker = new LinkedList<Integer>();
        // add node to queue
        queue.add(s);
        distanceTracker.add(0);
        // mark the first node as visited
        visited[s] = true;
        // do this until the queue is empty or we found t
        while(queue.size() > 0) {
            // dequeue the node
            int node = queue.remove();
            int currDist = distanceTracker.remove() + 1;
            // mark the node as visited
            // get all the neighbors of node
            ArrayList<Integer> neighbors = adj[node];
            // loop through each neighbor
            for(int i = 0; i < neighbors.size(); i++) {
                int neighborNode = neighbors.get(i);
                // if we reach the target, then we just return the currDist
                if(neighborNode == t) {
                    return currDist;
                }
                // if the node hasn't been visited, add it to the queue
                if(!visited[neighborNode]) {
                    queue.add(neighborNode);
                    // mark node as visited
                    visited[neighborNode] = true;
                    distanceTracker.add(currDist);
                }
            }
        };

        return -1;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}

