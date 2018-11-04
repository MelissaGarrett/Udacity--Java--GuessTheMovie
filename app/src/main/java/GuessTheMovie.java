import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GuessTheMovie {
    private static ArrayList<String> movies = new ArrayList<>(50);
    private static ArrayList<Character> userGuess = new ArrayList<>(25);
    private static ArrayList<Character> wrongLetters = new ArrayList<>(10);
    private static Scanner scanner;
    private static String randomMovie = "";
    private static int incorrectGuesses = 0;

    private static final int MAX_NUM_OF_GUESSES = 10;

    public static void main(String [] args) throws FileNotFoundException {
        File file = new File("movies.txt");
        scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String movieTitle = scanner.nextLine().trim();

            movies.add(movieTitle);
        }

        scanner.close();

        startGame();
    }

    private static void startGame() {
        boolean gameOver = false;

        scanner = new Scanner(System.in);

        int randomNumber = (int)(Math.random() * movies.size());
        randomMovie = movies.get(randomNumber);

        // User will see '_' indicating the number of letters in the word
        for (int i = 0; i < randomMovie.length(); i++) {
            userGuess.add('_');
        }

        while (!gameOver) {

            System.out.println("\nYou are guessing: " + convertUserGuess());
            System.out.println("You have guessed (" + incorrectGuesses + ") wrong letters: " + convertWrongLetters());
            System.out.print("Guess a letter: ");

            // User should only enter one character, but just in case...
            checkGuess(scanner.next(".").charAt(0));

            if (randomMovie.toLowerCase().equals(convertUserGuess().toLowerCase())) {
                System.out.println("You win!\nYou have guessed '" + randomMovie + "' correctly.");
                gameOver = true;
            } else if (incorrectGuesses == MAX_NUM_OF_GUESSES) {
                System.out.println("You're out of guesses.\nThe correct answer is " + randomMovie);
                gameOver = true;
            }
        }
    }

    // Convert ArrayList to String for displaying output
    private static String convertUserGuess() {
        String stringifiedGuess = "";

        for (int i = 0; i < userGuess.size(); i++) {
            stringifiedGuess += userGuess.get(i);
        }

        return stringifiedGuess;
    }

    // Convert ArrayList to String for displaying output
    private static String convertWrongLetters() {
        String stringifiedLetters = "";

        for (int i = 0; i < wrongLetters.size(); i++) {
            stringifiedLetters += wrongLetters.get(i) + " ";
        }

        return stringifiedLetters.trim();
    }

    // Check if character user entered is contained in the name of the randomMovie
    // If so, update userGuess with the found characters
    private static void checkGuess(Character character) {
        boolean foundCharacter = false;

        for (int i = 0; i < randomMovie.length(); i++) {
            if (Character.toLowerCase(randomMovie.charAt(i)) == Character.toLowerCase(character)) {
                    userGuess.set(i, character);
                    foundCharacter = true;
            }
        }

        if (!foundCharacter) {
            wrongLetters.add(character);
            incorrectGuesses++;
        }
    }
}
