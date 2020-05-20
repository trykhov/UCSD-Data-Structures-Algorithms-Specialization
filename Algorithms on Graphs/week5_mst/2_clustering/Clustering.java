import java.util.Scanner;
import java.util.Random;

public class Clustering {

    // have a vertex class to create vertices
    private static class Vertex {
        private Vertex parent;
        private int x;
        private int y;

        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
            this.parent = this;
        }

    }

    // have edge class to create edges between two vertices
    private static class Edge {
        private Vertex vertexA;
        private Vertex vertexB;
        private double dist;

        public Edge(Vertex a, Vertex b, double dist) {
            this.vertexA = a;
            this.vertexB = b;
            this.dist = dist; 
        }
    }

    // disjoint set functions
    private static Vertex find(Vertex a) {
        if(a.parent == a) {
            return a;
        }
        Vertex rep = find(a.parent);
        a.parent = rep;
        return a.parent;
    }

    private static void union(Vertex a, Vertex b) {
        Vertex aRep = find(a);
        Vertex bRep = find(b);
        aRep.parent = bRep;
    }

    // determine the distance between two points
    private static double distance(Vertex a, Vertex b) {
        int xSide = a.x - b.x;
        int ySide = a.y - b.y;
        return Math.sqrt((xSide * xSide) + (ySide * ySide));
    }

    // swapping elements
    private static void swap(Edge[] edges, int i, int j) {
        Edge temp = edges[i];
        edges[i] = edges[j];
        edges[j] = temp;
    }

    // sorting algorithm
    private static void quickSort(Edge[] edges, int left, int right) {
        // only run if array has a length of at least 1
        if(left < right) {
            // select a random index
            Random rand = new Random();
            int randIdx = rand.nextInt((right - left)) + left;
            // swap the randIdx with the edges[right]
            swap(edges, randIdx, right);
            int i = left;
            for(int j = left; j <= right; j++) {
                // move everything less than the pivot to the left of the array
                if(edges[j].dist < edges[right].dist) {
                    swap(edges, i, j);
                    i++;
                }
            }
            // place edges[right] to the correct place in the array
            swap(edges, i, right);
            quickSort(edges, left, i - 1);
            quickSort(edges, i + 1, right);
        }
    }

    private static double clustering(int[] x, int[] y, int k) {
        //write your code here
        // want to store the vertices to have references to the objects
        Vertex[] vertices = new Vertex[x.length];
        for(int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(x[i], y[i]);
        }
        // there are E <= Vˆ2 edges
        int totalEdges = x.length * (x.length - 1) / 2;
        Edge[] edges = new Edge[totalEdges];
        int e = 0;
        for(int i = 0; i < x.length; i++) {
            Vertex a = vertices[i];
            for(int j = i + 1; j < y.length; j++) {
                Vertex b = vertices[j];
                double dist = distance(a, b);
                edges[e] = new Edge(a, b, dist);
                e++;
            }
        }
        // sort the edges
        quickSort(edges, 0, edges.length - 1);
        // keep track of which edges are selected to be part of the MST
        // there are V - 1 edges in an MST
        Edge[] mstEdges = new Edge[x.length - 1];
        int mE = 0;
        // go through the edges in increasing order
        for(int i = 0; i < edges.length; i++) {
            Edge edge = edges[i];
            Vertex a = edge.vertexA;
            Vertex b = edge.vertexB;
            // determine if they form a cycle
            if(find(a) != find(b)) {
                union(a, b);
                // add the edge to the MST set of edges
                mstEdges[mE] = edge;
                mE += 1;
            }
        }
        // after collecting all the MST edges, the last k - 1 edges are edges that connect the k clusters
        // (v - k)th edge is the least of the k - 1 edges, that is the minimum distance from each cluster
        return mstEdges[x.length - k].dist;
        // return -1.;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
    }
}

