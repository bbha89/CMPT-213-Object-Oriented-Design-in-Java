package ca.cmpt213.a4.onlinehangman.model;

import java.util.ArrayList;

/**
 * Game class models the information about a game object
 * Information includes id, word, guesses, incorrect guess,
 * status, game image number.
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class Game {
    private long id;
    private String word;
    private int numOfGuesses;
    private int incorrectGuesses;
    private String status;
    private int imageNumber;

    private ArrayList<String> wordSpace = new ArrayList<>();
    private boolean pressed = false;
    private String userInput;

    public Game() {
    }

    public Game(long id, String word, int numOfGuesses, int incorrectGuesses, String status, int imageNumber) {
        this.id = id;
        this.word = word;
        this.numOfGuesses = numOfGuesses;
        this.incorrectGuesses = incorrectGuesses;
        this.status = status;
        this.imageNumber = imageNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNumOfGuesses() {
        return numOfGuesses;
    }

    public void setNumOfGuesses(int numOfGuesses) {
        this.numOfGuesses = numOfGuesses;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public void setIncorrectGuesses(int incorrectGuesses) {
        this.incorrectGuesses = incorrectGuesses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getWordSpace() {
        return wordSpace;
    }

    public void setWordSpace(ArrayList<String> wordSpace) {
        this.wordSpace = wordSpace;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", numOfGuesses=" + numOfGuesses +
                ", incorrectGuesses=" + incorrectGuesses +
                ", status='" + status + '\'' +
                ", imageNumber=" + imageNumber +
                ", wordSpace=" + wordSpace +
                ", pressed=" + pressed +
                ", userInput='" + userInput + '\'' +
                '}';
    }
}
