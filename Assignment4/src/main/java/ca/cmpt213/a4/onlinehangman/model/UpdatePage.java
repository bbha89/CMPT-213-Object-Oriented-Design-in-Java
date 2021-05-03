package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Updates a game session depending on user input or start of a new game.
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class UpdatePage {
    private static final int MAX_INCORRECT = 8;

    /**
     * Checks if game id exist
     */
    public Boolean checkIdExist(long idNumber, List<Game> game) {
        for (Game value : game) {
            if (value.getId() == idNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update game status
     */
    public void updateGameStatus(Game session, boolean input) {
        if (input) {
            session.setStatus("won");
        } else if (session.getIncorrectGuesses() == MAX_INCORRECT) {
            session.setStatus("lost");
        }
    }

    /**
     * Randomly picks a word from text file
     */
    public String getRandomWord() throws FileNotFoundException {
        String word;
        Scanner s = new Scanner(new File("./src/commonWords.txt"));
        ArrayList<String> words = new ArrayList<>();
        while (s.hasNext()){
            words.add(s.next());
        }
        s.close();
        Collections.shuffle(words);
        word = words.get(0);
        return word;
    }

    /**
     * adds underlines depending on word length
     */
    public ArrayList<String> initializeSpacing(String curWord) {
        ArrayList<String> wordSpace = new ArrayList<>();
        for (int i = 0; i < curWord.length(); i++) {
            wordSpace.add("_");
        }
        return wordSpace;
    }

    /**
     * Checks if user input is a letter in word
     */
    private Boolean checkGuess(String word, String userInput) {
        char userGuess = userInput.charAt(0);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == userGuess) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates guesses and updates progress of game if user input
     * is part of word. Checks if all letters have been found
     */
    public Boolean checkUserInput(Game session, Game userAnswer) {
        session.setNumOfGuesses(session.getNumOfGuesses()+1);
        String word = session.getWord();
        Boolean letterFound = checkGuess(word, userAnswer.getUserInput());
        if (letterFound) {
            ArrayList<String> wordSpace = updateSpacing(session.getWordSpace(), word, userAnswer.getUserInput());
            session.setWordSpace(wordSpace);
        } else {
            session.setIncorrectGuesses(session.getIncorrectGuesses()+1);
            session.setImageNumber(session.getImageNumber()+1);
        }
        return checkFoundAllLetters(session.getWordSpace());
    }

    /**
     * Change underline to user input letter if part of word
     */
    private ArrayList<String> updateSpacing(ArrayList<String> wordSpace, String word, String userInput) {
        char userGuess = userInput.charAt(0);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == userGuess) {
                wordSpace.set(i, userInput);
            }
        }
        return wordSpace;
    }

    /**
     * returns true if user has guessed all the letters of a word correctly
     */
    private Boolean checkFoundAllLetters(ArrayList<String> wordSpace) {
        for (String s : wordSpace) {
            if (s.equals("_")) {
                return false;
            }
        }
        return true;
    }
}
