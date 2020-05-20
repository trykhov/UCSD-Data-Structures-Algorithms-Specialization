import java.util.*;

public class Dijkstra {

    private static class Node {
        private int index;
        private double dist;
        // tells us where in the priority queue to allow for sifting
        private int queuePos;
        // the nodes for the priority queue
        public Node(int index, double dist) {
            // allows easy access to the array to change distance
            this.index = index;
            this.dist = dist;
        }
    }

    private static class MinHeap {
        private ArrayList<Node> queue;
        private int length;
        public MinHeap() {
            this.queue = new ArrayList<>();
        }

        private Node peek() {
            return this.queue.get(0);
        }

        private void insertNode(Node node) {
            this.queue.add(node);
            int lastIdx = this.queue.size() - 1;
            node.queuePos = lastIdx;
            siftUp(lastIdx);
        }

        private void siftUp(int idx) {
            int parentIdx = idx / 2;
            Node parent = this.queue.get(parentIdx);
            Node child = this.queue.get(idx);
            if(parent.dist > child.dist) {
                // reassign the child idx in the queue
                child.queuePos = parentIdx;
                this.queue.set(parentIdx, child);

                // reassign the parent idx in the queue
                parent.queuePos = idx;
                this.queue.set(idx, parent);
                siftUp(parentIdx);
            }
        }

        private void siftDown(int idx) {
            Node leftChild = null;
            Node rightChild = null;
            Node parent = this.queue.get(idx);
            int leftIdx = (2 * idx) + 1;
            int rightIdx = (2 * idx) + 2;
            int length = this.queue.size();
            if(rightIdx >= length) {
                return;
            }
            // initialize the children
            if(leftIdx < length) {
                leftChild = this.queue.get(leftIdx);
            }
            if(rightIdx < length) {
                rightChild = this.queue.get(rightIdx);
            }
            Node currentNode = this.queue.get(idx);
            int nextIdx = idx;
            if(currentNode.dist > leftChild.dist) {
                nextIdx = leftIdx;
                currentNode = this.queue.get(leftIdx);
            }
            if(currentNode.dist > rightChild.dist) {
                nextIdx = rightIdx;
                currentNode = this.queue.get(rightIdx);
            }
            if(nextIdx != idx) {
                currentNode.queuePos = idx;
                this.queue.set(idx, currentNode);

                parent.queuePos = nextIdx;
                this.queue.set(nextIdx, parent);
                siftDown(nextIdx);
            }
        }
        
        private Node extractMin() {
            // save the root node
            Node minNode = this.queue.get(0);
            // swap the first node with the end
            int lastIdx = this.queue.size() - 1;
            Node lastNode = this.queue.get(lastIdx);
            // move the last node to the front
            lastNode.queuePos = 0;
            this.queue.set(0, lastNode);
            // remove the last node (since it's moved to the front)
            this.queue.remove(lastIdx);
            // fix the heap
            if(this.queue.size() > 0) {
                siftDown(0);
            }
            return minNode;
        }
        
    }

    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        boolean[] visited = new boolean[adj.length];
        // initialize all the nodes as not-visited
        Arrays.fill(visited, false);
        // the min-heap / priority queue
        MinHeap minHeap = new MinHeap();
        // the distance array will hold Node objects to allow for easy access to change distances
        Node[] distance = new Node[adj.length];
        distance[s] = new Node(s, 0);
        minHeap.insertNode(distance[s]);
        for(int i = 0; i < adj.length; i++) {
            if(i != s) {
                // have all the nodes have a distance of infinity
                distance[i] = new Node(i, Double.POSITIVE_INFINITY); 
                // add them to the priority queue
                minHeap.insertNode(distance[i]);
            }
        }
        ArrayList<Node> pQueue = minHeap.queue;
        while(pQueue.size() > 0) {
            // fix the heap
            Node node = minHeap.extractMin();
            visited[node.index] = true;
            // get neighbors
            ArrayList<Integer> neighbors = adj[node.index];
            for(int i = 0; i < neighbors.size(); i++) {
                Node neighbor = distance[neighbors.get(i)];
                int neighborIdx = neighbor.index;
                if(!visited[neighborIdx] && distance[neighborIdx].dist > (node.dist + cost[node.index].get(i))) {
                    distance[neighborIdx].dist = node.dist + cost[node.index].get(i);
                    minHeap.siftUp(neighbor.queuePos);
                }
            }
        }
        if(distance[t].dist != Double.POSITIVE_INFINITY) {
            return (int) distance[t].dist;
        }
        return -1;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}

