import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;

public class ConnectingPoints {
    private static class Point {
        private int x;
        private int y;
        private Point parent;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.parent = this;
        }
    }

    private static class DisjointSet {
        // will implement the compression path disjoint set data structure
        private static void union(Point a, Point b) {
            Point aParent = find(a);
            Point bParent = find(b);
            aParent.parent = bParent;
        }

        private static Point find(Point a) {
            if(a.parent == a) {
                return a;
            }
            Point root = find(a.parent);
            // compress the path
            a.parent = root;
            return a.parent;
        }
    }

    private static class Edge {
        private double distance;
        private Point p1;
        private Point p2;

        public Edge(double distance, Point p1, Point p2) {
            this.distance = distance;
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        // make a disjoint set where all the points have representatives to themselves
        DisjointSet disjointSet = new DisjointSet();
        // make the Point objects for each point and store into an array
        Point[] points = new Point[x.length];
        for(int i = 0; i < x.length; i++) {
            points[i] = new Point(x[i], y[i]);
        }
        // total edges is based on the arithmetic sum formula
        int totalEdges = x.length * (x.length - 1) / 2;
        Edge[] edges = new Edge[totalEdges];
        int k = 0;
        // first find the distances of all the points on the graph
        for(int i = 0; i < x.length; i++) {
            Point pt1 = points[i];
            for(int j = i + 1; j < y.length; j++) {
                Point pt2 = points[j];
                double dist = distance(pt1, pt2);
                edges[k] = new Edge(dist, pt1, pt2);
                k++;
            } 
        }
        // sort the edges (quick sort) in increasing order
        quicksort(edges, 0, edges.length - 1);
        // go thru the sorted distances:
        for(int i = 0; i < edges.length; i++) {
            Point p1 = edges[i].p1;
            Point p2 = edges[i].p2;
            // determine that the points of that distance are not in the same set
            if(disjointSet.find(p1) != disjointSet.find(p2)) {
                // add the distance to the result
                result += edges[i].distance;
                // then unionize them
                disjointSet.union(p1, p2);
            }
        }
        return result;
    }

    private static double distance(Point p1, Point p2) {
        int side1 = p1.x - p2.x;
        int side2 = p1.y - p2.y;
        double distance = Math.sqrt((side1 * side1) + (side2 * side2));
        return distance;
    }

    private static void quicksort(Edge[] edges, int left, int right) {
        // pick a random index
        if(left < right) {
            Random rand = new Random();
            int randomIdx = rand.nextInt((right - left)) + left;
            // swap the end with the random index
            swap(edges, randomIdx, right);
            int i = left;
            for(int j = left; j <= right; j++) {
                // move everything less than the pivot to the left side of the array
                if(edges[j].distance < edges[right].distance) {
                    swap(edges, i, j);
                    i++;
                }
            }
            // move the pivot to the correct position
            swap(edges, i, right);
            // will be an infinite loop if left = 0, and i - 1 = 1
            quicksort(edges, left, i - 1);
            quicksort(edges, i + 1, right);
        }
    }

    private static void swap(Edge[] edges, int i, int j) {
        Edge temp = edges[i];
        edges[i] = edges[j];
        edges[j] = temp;
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
        System.out.println(minimumDistance(x, y));
    }
}

