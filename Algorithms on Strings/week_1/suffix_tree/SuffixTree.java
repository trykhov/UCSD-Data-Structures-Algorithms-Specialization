import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
    class Node {
        // starting position of substring
        public int start;
        // length of substring
        public int end;
        // next nodes
        public HashMap<Character, Node> next;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
            this.next = new HashMap<Character, Node>();
        }
    }

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();
        // Implement this function yourself
        Node root = buildSuffixTree(text);
        Node parent = null;
        for(int i = 0; i < text.length(); i++) {
            char t = text.charAt(i);
            if(!root.next.containsKey(t)) {
                continue;
            }
            parent = root.next.get(t);
            // System.out.print(t);
            // System.out.print(" ");
            // System.out.print(parent.start);
            // System.out.print(" ");
            // System.out.print(parent.end);
            // System.out.println(" ");
            for(int j = i; j < text.length(); j++) {
                char c = text.charAt(j);
                if(j == parent.end) {
                    String sub = text.substring(parent.start, parent.end);
                    result.add(sub);
                }
                if(parent.next.containsKey(c)) {
                    parent = parent.next.get(c);
                }
            }
        }
        return result;
    }

    Node buildSuffixTree(String text) {
        Node root = new Node(-1,text.length());
        Node parent = null;
        for(int i = 0; i < text.length(); i++) {
            char cT = text.charAt(i);
            if(!root.next.containsKey(cT)) {
                Node newBranch = new Node(i, text.length());
                root.next.put(cT, newBranch);
                continue;
            }
            parent = root.next.get(cT);
            int k = parent.start;
            for(int j = i; j < text.length(); j++) {
                // // cS for character of suffix
                char cS = text.charAt(j);
                // // cP for character of text
                char cP = text.charAt(k);
                if(cS != cP) {
                    // System.out.print(j);
                    // System.out.print(" ");
                    // System.out.print(parent.end);
                    // System.out.println("");
                    Node split = new Node(j, parent.end);
                    if(j < parent.end) {
                        Node remain = new Node(k, parent.end);
                        parent.next.put(cP, remain);
                        parent.end = k - 1;
                    }
                    parent.next.put(cS, split);
                    break;
                } 
                if(parent.next.containsKey(cP)) {
                    parent = parent.next.get(cP);
                }
                k += 1;
            }
        }
        return root;
    }




    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
