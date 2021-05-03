package ca.cmpt213.a3.shapes;

import ca.cmpt213.a3.UI.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class drawing rectangles and adding message
 * inside it. Splits the message to fit words in each
 * line and has center alignment for the text
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class TextBox extends Rectangle{
    private String message;

    public TextBox(int x, int y, int width, int height, String message) {
        super(x, y, width, height);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *Set the text at a location in the shape
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        List<String> wholeMessage;
        int rowLength = getWidth() - 2;
        int colLength = getHeight() - 2;

        wholeMessage = splitUpMessage();

        for (int i = 0; (i < wholeMessage.size()) && (i < colLength); i++) {
            String currText = wholeMessage.get(i);
            int centerCurrLine = (rowLength - currText.length()) / 2;

            for (int j = centerCurrLine + 1; (j < centerCurrLine + currText.length() + 1); j++) {
                canvas.setCellText((getLocationX() + oddSpacingCheck(currText) + j), (getLocationY() + i + 1), currText.charAt(j - centerCurrLine - 1));
            }
        }
    }

    /**
     * Splits message to multiple lines if the
     * words are too long to fit in one line.
     */
    private ArrayList<String> splitUpMessage() {
        //Holds the whole message
        ArrayList<String> textBox = new ArrayList<>();
        int rowLength = getWidth() - 2;
        String currText = "";
        String leftSplit;
        String word;
        // splits string into substrings after each space
        String string = getMessage();
        String[] wordArray = string.split(" ");


        // iterate through each word, adding the word to the line whenever there is space
        for (int i = 0; i < wordArray.length; i++) {
            word = wordArray[i].trim(); //removes trailing and extra spaces

            if (currText.equals("")) {
                currText = word;
            } else if (currText.length() > rowLength) {                             // split the word where the border is reached
                leftSplit = currText.substring(0, rowLength);
                textBox.add(leftSplit);
                currText = currText.substring(rowLength);
                i--;
            } else if (word.length() + currText.length() > rowLength - 1) {         // length of message in one line is full
                textBox.add(currText);
                currText = "";
                i--;
            } else {
                currText += " " + word;
            }
            if ((string.charAt(0) == ' ') && (i == 0) && (rowLength % 2 == 1)) {    // Checks if the message starts with a space
                currText = " " + currText;
            }
        }

        //last word is split until fits in the line length
        while (currText.length() > rowLength) {
            leftSplit = currText.substring(0, rowLength);
            textBox.add(leftSplit);
            currText = currText.substring(rowLength);
        }
        textBox.add(currText);
        return textBox; // return the new array
    }

    /**
     * Add an extra space at the beginning of the first
     * word if there is an odd number of extra spaces
     */
    private int oddSpacingCheck(String currText) {
        int checkOddSpacing = getWidth() - currText.length();
        int addSpace = 0;
        // add space if odd number of spaces
        if (checkOddSpacing % 2 == 1) {
            addSpace = 1;
        }
        return addSpace;
    }

}
// source: https://stackoverflow.com/questions/32423346/splitting-a-string-into-two-halfs