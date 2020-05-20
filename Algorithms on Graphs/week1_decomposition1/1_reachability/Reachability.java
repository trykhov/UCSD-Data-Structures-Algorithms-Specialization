import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    // can you reach y from x ?
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        //write your code here
        // can do a DFS
        // start at x's neighbors and move towards y
        // if one of them can get to y, then x can get to y
        boolean[] visited = new boolean[adj.length];
        return dfs(adj, x, y, visited);
    }

    private static int dfs(ArrayList<Integer>[] adj, int s, int y, boolean[] visited) {
        // smallest subproblem: a node can always reach itself
        if(s == y) {
            return 1;
        } else if(visited[s]) {
            // dead end if this has been visited
            return 0;
        }

        ArrayList<Integer> neighbors = adj[s];
        visited[s] = true;
        for(int i = 0; i < neighbors.size(); i++) {
            // skip if the neighbor has been visited
            if(adj[neighbors.get(i)].size() != 0) {
                // do DFS on neighbors
                int reached = dfs(adj, neighbors.get(i), y, visited);
                // if there's a path, then return 1
                if(reached == 1) {
                    return 1;
                }
            }
        }
        // can't reach y from x
        return 0;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        // adj is an ArrayList that has stores n arrayList elements, those arrayList store integers
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            // x - 1 because there's no 0 node value
            adj[x - 1].add(y - 1); // adding node to arraylist in index x - 1
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}

