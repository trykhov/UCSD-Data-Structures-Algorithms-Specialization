import java.util.*;

public class ShortestPaths {

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
      //write your code here
      // start the source at distance 0
      distance[s] = 0;
      // s is always reachable from s
      reachable[s] = 1;
      // this will be for the BFS when we look for nodes in the infinite arbitrage
      // initialize arbitrage queue
      Queue<Integer> queue = new LinkedList<Integer>();
      // apply Bellman Ford
      // iterate over each node |V| times
      for(int i = 0; i < adj.length; i++) {
          for(int j = 0; j < adj.length; j++) {
              ArrayList<Integer> neighbors = adj[j];
              // prevents overflow
              if(distance[j] < Long.MAX_VALUE) {
                for(int k = 0; k < neighbors.size(); k++) {
                    int node = neighbors.get(k);
                    long prior = distance[node];
                    // relax each node
                    distance[node] = Math.min(distance[node], distance[j] + cost[j].get(k));
                    reachable[node] = 1;
                    // if on the |V|th iteration, a distance changes, add to the queue
                    if((i == adj.length - 1) && (distance[node] < prior)) {
                        queue.add(node);
                    }
                }
              }
          }
      }
    // do a BFS on the queue to find the nodes that are connected to the arbitrage
    // way to mark the node as visited and is part of the arbitrage
    // System.out.println(queue.peek());
      while(queue.size() > 0) {
          int node = queue.remove();
          shortest[node] = 0;
          ArrayList<Integer> neighbors = adj[node];
          for(int i = 0; i < neighbors.size(); i++) {
              int neighborNode = neighbors.get(i);
              if(shortest[neighborNode] == 1) {
                  queue.add(neighborNode);
                  shortest[neighborNode] = 0;
              }
          }
      }
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}

