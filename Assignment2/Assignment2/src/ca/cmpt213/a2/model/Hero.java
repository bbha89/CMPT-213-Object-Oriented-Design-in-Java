package ca.cmpt213.a2.model;

/**
 * Hero class models the information about a hero.
 * Information includes the heroes position and
 * is updated depending on direction.
 */
public class Hero {
    private static int xPos = 0;
    private static int yPos = 0;

    public Hero(int x, int y){
        xPos = x;
        yPos = y;
    }

    public int getX(){
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void moveRight() {
        yPos++;
    }

    public void moveLeft() {
        yPos--;
    }

    public void moveUp() {
        xPos--;
    }

    public void moveDown() {
        xPos++;
    }

}
