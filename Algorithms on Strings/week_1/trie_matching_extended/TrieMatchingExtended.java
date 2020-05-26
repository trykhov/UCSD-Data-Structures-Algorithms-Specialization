import java.io.*;
import java.util.*;

class Node
{
	public HashMap<Character, Node> next;
	public boolean patternEnd;

	Node ()
	{
		this.next = new HashMap<Character, Node>();
		this.patternEnd = false;
	}
}

public class TrieMatchingExtended implements Runnable {
	

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();
		// write your code here
		Node trieRoot = buildTrie(patterns);
		for(int i = 0; i < text.length(); i++) {
			Node parent = trieRoot;
			for(int j = i; j < text.length(); j++) {
				char c = text.charAt(j);
				if(!parent.next.containsKey(c)) {
					break;
				}
				parent = parent.next.get(c);
				// doesn't matter if there are other branches, it will still have the same starting index
				if(parent.patternEnd) {
					result.add(i);
					break;
				}
			}
		}
		return result;
	}

	Node buildTrie(List<String> patterns) {
		Node root = new Node();
		for(String pattern : patterns) {
			Node parent = root;
			for(int i = 0; i < pattern.length(); i++) {
				char c = pattern.charAt(i);
				if(!parent.next.containsKey(c)) {
					Node newBranch = new Node();
					parent.next.put(c, newBranch);
				}
				parent = parent.next.get(c);
			}
			parent.patternEnd = true;
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
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
