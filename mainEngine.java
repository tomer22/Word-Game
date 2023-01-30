import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class mainEngine {

    private static final String DICTIONARY_FILE_PATH = "oxfordDict.txt";

    public static void main(String[] args) {
        // Load the dictionary from the file
        List<String> dictionary = loadDictionary(DICTIONARY_FILE_PATH);
        // int MAX_GUESSES = 20;

        // Pick a random word from the dictionary
        String word = pickRandomWord(dictionary);

        // Create a list to store the user's correct guesses
        List<String> correctGuesses = new ArrayList<>();

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
        while (guess != "quit") {
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
            } else {
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
        List<String> dictionary = new ArrayList<>();
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
        //Gives 6 letter words?
        while (word.length() < 7) {
            Random random = new Random();
            int index = random.nextInt(dictionary.size());
            word = dictionary.get(index);
        }
        return word;
    }

    private static boolean isValidGuess(String word, String guess) {
        // Check if the guess can be formed using the letters in the word
        //This does not work as it should. For some reason the words length is 3 above its actual size and then when you guess the word it sees the lengths don't match
        word.toLowerCase();
        if (guess.length() > word.length()) {
            return false;
        } else if (guess.equals(word)) {
            System.out.println("guess is the same word as the given one");
            return false;
        }

        // Checking if the guess contains the same letter in all of them
        char[] letters = guess.toCharArray();
        ArrayList<Character> lettersList = new ArrayList<>();
        for (int i = 0; i < letters.length; i++) {
            lettersList.add(letters[i]);
        }

        for (int i = 0; i < lettersList.size(); i++) {
            for (int j = 0; j < word.length(); j++) {
                if (lettersList.get(i) == word.charAt(j)) {
                    lettersList.remove(lettersList.get(j));
                } 
            }
        }
        if (lettersList.size() > 0) {
            return false;
        }
        return true;

/* 
        String wordCopy = word;

        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            int index = wordCopy.indexOf(c);
            if (index == -1) {
                return false;
            }
        }
        return true;
        */
    }
}