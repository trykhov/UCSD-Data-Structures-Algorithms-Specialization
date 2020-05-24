import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        // write your code here
        // start the count at 1
        int count = 1;
        // go thru each pattern in patterns[]
        for(String pattern : patterns) {
            // always start at the root at beginning of each string
            int parent = 0;
            // go through each letter of the pattern
            for(int i = 0; i < pattern.length(); i++) {
                // get the character of the string
                Character c = pattern.charAt(i);
                // always add space to accommodate the count
                if(trie.size() < count) {
                    Map<Character, Integer> newBranch = new HashMap<Character, Integer>();
                    trie.add(newBranch);
                }
                // look at the parent
                Map<Character, Integer> branch = trie.get(parent);
                // see if the branch already has the letter
                if(!branch.containsKey(c)) {
                    // if it doesn't, add the character and the count
                    branch.put(c, count);
                    // the count is the new parent now
                    parent = count;
                    count += 1;
                } else {
                    // if the letter is already present, go down the tree path
                    parent = branch.get(c);
                }
            }
        }
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
