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
        Integer[] leftSide = new Integer[bwt.length()];
        // create a count array to hold the indices of characters
        LinkedList[] countArray = new LinkedList[4];
        for(int i = 0; i < countArray.length; i++) {
            countArray[i] = new LinkedList<Integer>();
        }
        // create a hashmap that maps the characters to the index
        HashMap<Character, Integer> hashmap = new HashMap<Character, Integer>();
        hashmap.put('A', 0);
        hashmap.put('C', 1);
        hashmap.put('G', 2);
        hashmap.put('T', 3);
        
        for(int i = 0; i < bwt.length(); i++) {
            char character = bwt.charAt(i);
            // fill the leftSide's first index
            if(character == '$') {
                leftSide[0] = i;
            } else {
                // put the index in the countArray stacks
                int idx = hashmap.get(character);
                countArray[idx].add(i);
            }
        }
        // start the leftSide index at 1 (0 is already filled)
        int l = 1;
        for(int i = 0; i < countArray.length; i++) {
            LinkedList currQueue = countArray[i];
            while(currQueue.size() > 0) {
                leftSide[l++] = (Integer) currQueue.remove();
            }
        }

        int idx = leftSide[0];
        for(int i = 0; i < bwt.length(); i++) {
            idx = leftSide[idx];
            char letter = bwt.charAt(idx);
            result.append(letter);
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
