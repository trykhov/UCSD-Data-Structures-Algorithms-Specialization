import java.io.*;
import java.util.*;

class Node {
	public char letter; 
	public Map<Character, Node> next;

	// a trie node
	Node (char c) {
		this.letter = c;
		this.next = new HashMap<Character, Node>();
	}
}

public class TrieMatching implements Runnable {

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();
		// write your code here
		// build the trie and get the root
		Node trieRoot = buildTrie(patterns);
		for(int i = 0; i < text.length(); i++) {
			Node parent = trieRoot;
			for(int j = i; j < text.length(); j++) {
				char c = text.charAt(j);
				// current node is a leaf
				if(!parent.next.containsKey(c)) {
					break;
				}
				parent = parent.next.get(c);
				if(parent.next.isEmpty()) {
					result.add(i);
					break;
				}
			}
		}
		return result;
	}

	Node buildTrie(List <String> patterns) {
		// initialize the root
		Node root = new Node('R');
		// create a trie using the patterns (like the first problem)
		for(String pattern : patterns) {
			// always start at the root
			Node parent = root;
			// go through each letter and add them to the trie
			for(int i = 0; i < pattern.length(); i++) {
				char c = pattern.charAt(i);
				// determine if the parent's next already as c
				if(!parent.next.containsKey(c)) {
					// create a new branch
					Node newBranch = new Node(c);
					parent.next.put(c, newBranch);
				}
				parent = parent.next.get(c);
			}
		}
		return root;
	}


	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);
			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatching ()).start ();
	}
}
