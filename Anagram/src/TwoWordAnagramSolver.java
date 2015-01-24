import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TwoWordAnagramSolver {
	private static BufferedReader br;
	private static String path = "wordlist.txt";
	private static ArrayList<String> words;
	private static ArrayList<String> results = new ArrayList<String>();
	private static Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

	public static void main(String[] args) {
		System.out.println("please insert your Word: ");
		String inputWord = getInput();
		inputWord = inputWord.substring(0, inputWord.length() - 2);

		try {
			br = new BufferedReader(new FileReader(path));
			words = buildWordlistFromFile();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to load wordlist");
		}

		buildHashMap();
		results = solveAnagram(inputWord);
		solveTwoWordAnagram(inputWord);

		// if (!(results.isEmpty()) && !(results.contains(inputWord))) {
		if (!(results.isEmpty())) {
			for (String s : results) {
				System.out.println(s);
			}
		} else {
			System.out.println("No words were found that match that anagram!");
		}
	}

	// returns an ArrayList loaded with all the words from my wordlist
	private static ArrayList<String> buildWordlistFromFile() throws IOException {

		ArrayList<String> words = new ArrayList<String>();
		String temp;

		while ((temp = br.readLine()) != null) {
			words.add(temp);
		}

		return words;
	}

	// builds a hashmap of the words in the wordlist. Uses the sorted word as
	// the key
	private static void buildHashMap() {

		for (String word : words) {
			String sortedWord = sortString(word);

			if (map.containsKey(sortedWord)) {
				// add wod to arraylist, if the key alredy exists
				map.get(sortedWord).add(word);
			} else {
				ArrayList<String> a = new ArrayList<String>();
				a.add(word);
				// create new arraylist with a new key
				map.put(sortedWord, a);
			}
		}
	}

	// sorts a string by its characters, the idea being an anagram sorts to the
	// same string as the word it comes from
	private static String sortString(String s) {

		char[] c = s.toLowerCase().toCharArray();
		Arrays.sort(c);

		return String.valueOf(c);
	}

	// returns the lenth of each word
	private static ArrayList<String> solveTwoWordAnagram(String anagram) {

		Set<String> keys = map.keySet();
		for (String keyOne : keys) {
			int keyOneLength = keyOne.length();
			for (String keyTwo : keys) {
				int keyTwoLenth = keyTwo.length();

				if ((keyTwoLenth + keyOneLength) == anagram.length()) {
					System.out.println(keyOne + " " + keyTwo);
				}
			}
		}

		return null;
	}

	// returns the arraylist mapped to the value of the sorted anagram string if
	// it exists
	private static ArrayList<String> solveAnagram(String anagram) {
		String sortedAnagram = sortString(anagram);
		if (map.containsKey(sortedAnagram)) {
			return map.get(sortedAnagram);

		} else {
			return new ArrayList<String>();
		}

	}

	// returns the input word
	public static String getInput() {
		String s = "";
		try {
			boolean isComplete = false;
			while (!isComplete) {
				char c = (char) System.in.read();
				s += c;
				isComplete = System.in.available() == 0;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to read input");
		}
		return s;
	}
}
