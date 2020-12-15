import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        // write your code here
        StringBuilder result = new StringBuilder(text.length());
        // store the strings into an array of size text.length()
        String[] permutations = new String[text.length()];
        String permute = text;
        for(int i = 0; i < text.length(); i++) {
            permutations[i] = permute;
            permute = rotateString(permute);
        }
        Arrays.sort(permutations);
        for(int i = 0; i < permutations.length; i++) {
            char lastChar = permutations[i].charAt(text.length() - 1);
            result.append(lastChar);
        }
        return result.toString();
    }

    String rotateString(String text) {
        StringBuilder output = new StringBuilder(text.length());
        char lastChar = text.charAt(text.length() - 1);
        output.append(lastChar);
        for(int i = 0; i < text.length() - 1; i++) {
            char character = text.charAt(i);
            output.append(character);
        }
        return output.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
