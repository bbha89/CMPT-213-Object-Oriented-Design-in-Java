package ca.cmpt213.a3.shapes;
import ca.cmpt213.a3.UI.Canvas;
import java.awt.*;

/**
 * Abstract class providing shared functionalities required
 * by its derived classes
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public abstract class ShapeImp1 implements Shape {

    private final int locationX, locationY;
    private final int width, height;
    private char borderChar = '*';
    private Color color = Color.blue;

    public ShapeImp1(int x, int y, int width, int height) {
        locationX = x;
        locationY = y;
        this.width = width;
        this.height = height;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setBorderChar(char borderChar) {
        this.borderChar = borderChar;
    }

    public char getBorderChar() {
        return borderChar;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    /**
     *Sets the shapes border and color
     */
    public void draw(Canvas canvas) {
        for (int i = 0; i <= width - 1; i++) {
            for (int j = 0; j <= height - 1; j++) {
                int xPos = locationX + i;
                int yPos = locationY + j;
                if (isBorder(i, j)) {
                    canvas.setCellText(xPos, yPos, borderChar);
                    canvas.setCellColor(xPos, yPos, color);
                } else if (isInside(i, j)) {
                    canvas.setCellColor(xPos, yPos, color);
                    canvas.setCellText(xPos, yPos, ' ');
                }
            }
        }
    }

    protected abstract boolean isBorder(int xPos, int yPos);

    protected abstract boolean isInside(int xPos, int yPos);

}
