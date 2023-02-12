import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class mainEngine {

    // Create a list to store the user's correct guesses
    static List<String> correctGuesses = new ArrayList<>();

    private static final String DICTIONARY_FILE_PATH = "notepad.txt";

    public static void main(String[] args) {
        // Load the dictionary from the file
        List<String> dictionary = loadDictionary(DICTIONARY_FILE_PATH);
        // int MAX_GUESSES = 20;

        // Pick a random word from the dictionary
        String word = pickRandomWord(dictionary);
        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Start the game
        System.out.println("Welcome to the Word Game!");
        System.out.println(
                "The computer has chosen a random word, and your goal is to guess as many words as possible using the letters in that word.");
        // System.out.println("You have " + MAX_GUESSES + " chances to guess words.");
        System.out.println("Enter 'quit' at any time to end the game and see your score.");
        System.out.println();
        System.out.println("The word is: " + word);

        // Loop until the user quits the game -- The condition for exiting the loop will
        // change
        String guess = "";
        while (!guess.equals("quit")) {
            System.out.print("Enter a word: ");
            guess = scanner.nextLine().toLowerCase();

            // Check if the word is a valid guess
            if (isValidGuess(word, guess) && dictionary.contains(guess)) {
                if (!correctGuesses.contains(guess)) {
                    correctGuesses.add(guess);
                    System.out.println("Correct!");
                } else {
                    System.out.println("This word was already guessed");
                }
            }
            else {
                System.out.println("Incorrect.");
                
            }
        }

        // Print the score and the list of correct guesses
        System.out.println();
        System.out.println("Game over.");
        System.out.println("Your score is: " + correctGuesses.size());
        System.out.println("Correct guesses: " + correctGuesses);
    }

    private static List<String> loadDictionary(String filePath) {
        List<String> dictionary = new ArrayList<String>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                dictionary.add(scanner.nextLine());
            }
            // Error reading the file
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }
        return dictionary;
    }

    private static String pickRandomWord(List<String> dictionary) {
        // Error checking -- Will be deleted later
        if (dictionary == null || dictionary.isEmpty()) {
            throw new IllegalArgumentException("Dictionary cannot be null or empty");
        }

        String word = "";
        while (word.length() < 8) {
            Random random = new Random();
            int index = random.nextInt(dictionary.size());
            word = dictionary.get(index);
        }
        return word;
    }

    public static boolean isValidGuess(String word, String guess) {
        word = word.toLowerCase();
        guess = guess.toLowerCase();
        int[] wordCounts = new int[26];
        int[] guessCounts = new int[26];

        if (guess.length() > word.length()) {
            return false;
        } else if (guess.equals(word)) {
            System.out.println("guess is the same word as the given one");
            return false;
        }
    
        // Count the occurrences of each letter in the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            wordCounts[c - 'a']++;
        }
    
        // Count the occurrences of each letter in the guess
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            guessCounts[c - 'a']++;
        }
    
        // Check if the counts of each letter in the guess are less than or equal to the counts in the word
        for (int i = 0; i < 26; i++) {
            if (guessCounts[i] > wordCounts[i]) {
                return false;
            }
        }
    
        return true;
    }
    
}