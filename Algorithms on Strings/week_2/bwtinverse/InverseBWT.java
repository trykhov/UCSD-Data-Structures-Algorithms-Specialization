import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class InverseBWT {
    private static class Node {
        private char character;
        private int idx;

        public Node(char character, int idx) {
            this.character = character;
            this.idx = idx;
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        Node[] leftSide = new Node[bwt.length()];
        for(int i = 0; i < bwt.length(); i++) {
            char character = bwt.charAt(i);
            Node node = new Node(character, i);
            leftSide[i] = node;
        }
        // sort leftSide by character
        Arrays.sort(leftSide, new Comparator<Node>() {
            // @Override
            public int compare(Node s1, Node s2) {
                return Character.compare(s1.character, s2.character);
            }
        });
        // write your code here
        int nodeIdx = leftSide[0].idx;
        for(int i = 0; i < bwt.length(); i++) {
            char letter = leftSide[nodeIdx].character;
            result.append(letter);
            nodeIdx = leftSide[nodeIdx].idx;
        }
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
