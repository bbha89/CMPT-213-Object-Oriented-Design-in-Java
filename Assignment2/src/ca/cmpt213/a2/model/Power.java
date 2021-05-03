package ca.cmpt213.a2.model;

/**
 * Power class models the information about the power.
 * Information includes the position of the power.
 */
public class Power {
    private static int xPos = 0;
    private static int yPos = 0;
    public Power(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getX() {
       return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int randomX) {
        xPos = randomX;
    }

    public void setY(int randomY) {
        yPos = randomY;
    }


}
