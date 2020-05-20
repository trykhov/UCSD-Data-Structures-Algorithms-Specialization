import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class FriendSuggestion {
    private static class Node {
       private long minDistance;
       private int idxRef;
       private int queuePos;

       public Node(int idxRef, long minDistance) {
           this.idxRef = idxRef; 
           this.minDistance = minDistance;
       }
    }

    private static class MinHeap {
        private ArrayList<Node> queue;
        
        public MinHeap() {
            this.queue = new ArrayList<Node>();
        }

        // returns the parent of a node
        Node parent(Node node) {
            return this.queue.get(node.queuePos / 2);
        }

        // returns the children of a node
        Node leftChild(Node node) {
            int idx = (2 * node.queuePos) + 1;
            return this.queue.get(idx);
        }

        Node rightChild(Node node) {
            int idx = (2 * node.queuePos) + 2;
            return this.queue.get(idx);
        }

        void insertNode(Node node) {
            this.queue.add(node);
            int lastIdx = this.queue.size() - 1;
            node.queuePos = lastIdx;
            siftUp(node);
        }

        void siftUp(Node node) {
            // get the parent
            Node parent = parent(node);
            int idx = node.queuePos;
            if(parent.minDistance > node.minDistance) {

                node.queuePos = parent.queuePos;
                this.queue.set(parent.queuePos, node);

                parent.queuePos = idx;
                this.queue.set(idx, parent);

                siftUp(node);
            }
        }

        void siftDown(Node node) {
            int idx = node.queuePos;
            int queueLen = this.queue.size();

            if(queueLen < 1) {
                return;
            }

            Node leftChild = null;
            Node rightChild = null;
            
            // determine if the node has children
            if(2 * idx + 1 < queueLen) {
                leftChild = leftChild(node);
            }

            if(2 * idx + 2 < queueLen) {
                rightChild = rightChild(node);
            }

            // compare the priority of the node with its children
            Node currNode = node;
            if((leftChild != null) && (currNode.minDistance > leftChild.minDistance)) {
                currNode = leftChild;
            } 
            if((rightChild != null) && (currNode.minDistance > rightChild.minDistance)) {
                currNode = rightChild;
            }

            if(currNode != node) {
                // switch positions
                int pos = node.queuePos;
                node.queuePos = currNode.queuePos;
                this.queue.set(currNode.queuePos, node);

                currNode.queuePos = pos;
                this.queue.set(pos, currNode);
                // continue sifting down
                siftDown(node);
            }
        }

        Node extractMin() {
            // swap the root with the last node
            Node minNode = this.queue.get(0);

            int lastIdx = this.queue.size() - 1;
            Node lastNode = this.queue.get(lastIdx);
            // put the last node at the root
            lastNode.queuePos = 0;
            this.queue.set(0, lastNode);
            // remove the last node (it's a duplicate)
            this.queue.remove(lastIdx);
            siftDown(lastNode);
            return minNode;
        }

    }

    private static class Impl {
        // Number of nodes
        int n;
        // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
        // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
        // and cost stores their costs.
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        // distance[0] and distance[1] correspond to distance estimates in the forward and backward searches.
        Long[][] distance;
        // visited[v] == true iff v was visited either by forward or backward search.
        boolean[] visited;
        // List of all the nodes which were visited either by forward or backward search.
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;

        Impl(int n) {
            this.n = n;
            visited = new boolean[n];
            Arrays.fill(visited, false);
            workset = new ArrayList<Integer>();
            distance = new Long[][] {new Long[n], new Long[n]};
            for (int i = 0; i < n; ++i) {
                distance[0][i] = distance[1][i] = INFINITY;
            }
        }

        // Reinitialize the data structures before new query after the previous query
        void clear() {
            for (int v : workset) {
                distance[0][v] = distance[1][v] = INFINITY;
                visited[v] = false;
            }
            // remove all elements from the workset list
            workset.clear();
        }

        // Try to relax the distance from direction side to node v using value dist.
        void visit(int side, int v, Long[][] distance, Node[] ref, MinHeap heap) {
            // Implement this method yourself
            ArrayList<Integer> neighbors = adj[side][v];
            for(int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
                if(ref[neighbor] == null) {
                    ref[neighbor] = new Node(neighbor, distance[side][neighbor]);
                    heap.insertNode(ref[neighbor]);
                    workset.add(neighbor);
                }
                // update only non-visited nodes
                if(!visited[neighbor]) {
                    long prior = distance[side][neighbor];
                    long dist = (long) cost[side][v].get(i) + distance[side][v];
                    if(dist < prior) {
                        // update the distance
                        distance[side][neighbor] = dist;
                        ref[neighbor].minDistance = dist;
                        // change its priority 
                        heap.siftUp(ref[neighbor]);
                    }
                }
            }
        }

        Long processShortestPath() {
            // process the shortest path
            long dist = INFINITY;
            for(int u : workset) {
                dist = Math.min(dist, distance[0][u] + distance[1][u]);
            }
            return dist == INFINITY ? -1L : dist;
        }

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            // clears everything before running
            clear();
            // mark the starting point of each search with 0 distance
            distance[0][s] = 0L;
            workset.add(s);

            distance[1][t] = 0L;
            workset.add(t);
            // Implement the rest of the algorithm yourself
            // used to store the references of the nodes in the heap
            Node[] gRef = new Node[n];
            Node[] rgRef = new Node[n];
            // initialize the heaps
            MinHeap gHeap = new MinHeap();
            MinHeap rgHeap = new MinHeap();
            // insert the vertices and their distances in the heap
            gRef[s] = new Node(s, distance[0][s]);;
            gHeap.insertNode(gRef[s]);

            rgRef[t] = new Node(t, distance[1][t]);
            rgHeap.insertNode(rgRef[t]);

            while((gHeap.queue.size() > 0) && (rgHeap.queue.size() > 0)) {
                // forward search
                Node v = gHeap.extractMin();
                visit(0, v.idxRef, distance, gRef, gHeap);
                if(visited[v.idxRef]) {
                    return processShortestPath();
                }
                visited[v.idxRef] = true;

                // backwards search
                Node w = rgHeap.extractMin();
                visit(1, w.idxRef, distance, rgRef, rgHeap);
                if(visited[w.idxRef]) {
                    return processShortestPath();
                }
                visited[w.idxRef] = true;
            }
            return -1L;
        }
    }



    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Impl bidij = new Impl(n);
        // initialize the adjacent array to have two arrays, one for G and the other for Gr
        bidij.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        // initialize the cost array for G and GR
        bidij.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            bidij.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            bidij.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            // set the neighbors of each vertex to be an empty arraylist
            for (int i = 0; i < n; i++) {
                // neighbors
                bidij.adj[side][i] = new ArrayList<Integer>();
                // cost of each connecting neighbors
                bidij.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            // going forward
            bidij.adj[0][x - 1].add(y - 1);
            bidij.cost[0][x - 1].add(c);
            // going backwards
            bidij.adj[1][y - 1].add(x - 1);
            bidij.cost[1][y - 1].add(c);
        }
        // number of nodes to examine
        int t = in.nextInt();
        // which nodes to examine
        for (int i = 0; i < t; i++) {
            int u, v;
            // starting node
            u = in.nextInt();
            // ending node
            v = in.nextInt();
            // printing solution
            System.out.println(bidij.query(u-1, v-1));
        }
    }
}
