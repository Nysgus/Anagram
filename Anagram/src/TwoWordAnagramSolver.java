import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TwoWordAnagramSolver {
	private static String path = "wordlist.txt";
	private static ArrayList<String> words;
	private static ArrayList<String> results = new ArrayList<String>();
	private static ArrayList<String> twoWordAnagram = new ArrayList<String>();
	private static Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

	public static void main(String[] args) {
		System.out.println("please insert your Word: ");
		long startTime = System.currentTimeMillis();
		String inputWord = getInput();
		inputWord = inputWord.substring(0, inputWord.length() - 2);

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			words = buildWordlistFromFile(br, inputWord);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to load wordlist");
		}

		buildHashMap();
		results = solveAnagram(inputWord);
		twoWordAnagram = solveTwoWordAnagram(inputWord);

		// if (!(results.isEmpty()) && !(results.contains(inputWord))) {
		if (!(results.isEmpty())) {
			System.out.println("Found Anagrams: ");
			for (String s : results) {
				System.out.println(s);
			}
		} else {
			System.out.println("No words were found that match that anagram!");
		}
		if (!(twoWordAnagram.isEmpty())) {
			System.out.println("Found TwoWordAnagrams: ");
			for (String s : twoWordAnagram) {
				System.out.println(s);
			}
		} else {
			System.out.println("No two words were found that match that anagram!");
		}

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("Time spent: " + duration);
	}

	// returns an ArrayList loaded with all the words from my wordlist
	private static ArrayList<String> buildWordlistFromFile(BufferedReader br, String inputWord) throws IOException {

		ArrayList<String> words = new ArrayList<String>();
		String temp;

		while ((temp = br.readLine()) != null) {
			if (contains(temp.toCharArray(), inputWord.replaceAll("\\s", "").toLowerCase().toCharArray())) {
				words.add(temp);
			}
		}

		return words;
	}

	private static boolean contains(char[] charArray1, char[] charArray2) {
		if (charArray1.length > charArray2.length) {
			return false;
		}
		List<Character> charList1 = toList(charArray1);
		List<Character> charList2 = toList(charArray2);

		for (Character charValue : charList1) {
			if (charList2.contains(charValue)) {
				charList2.remove(charValue);
			} else {
				return false;
			}
		}
		return true;
	}

	private static List<Character> toList(char[] charArr) {
		assert charArr != null;
		List<Character> charList = new ArrayList<Character>();
		for (char ch : charArr) {
			charList.add(ch);
		}
		return charList;
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

	// returns the arraylist of the two words which maps the anagram
	private static ArrayList<String> solveTwoWordAnagram(String anagram) {

		String sortedAnagram = sortString(anagram);

		Set<String> keys = map.keySet();

		for (String keyOne : keys) {
			for (String keyTwo : keys) {
				if ((keyTwo.length() + keyOne.length()) == anagram.length()) {
					String twoWord = keyOne + keyTwo;
					String sortedTwoWord = sortString(twoWord);
					if (sortedTwoWord.equals(sortedAnagram)) {
						twoWordAnagram.add((map.get(keyOne)) + " " + ((map.get(keyTwo))));
					}
				}
			}
		}
		System.out.println("twoword" + twoWordAnagram.size());
		return twoWordAnagram;
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
