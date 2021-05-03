package ca.cmpt213.a2.model;

/**
 * Monster class models the information of each monster.
 * Information include a monsters position and is updated
 * depending on direction.
 */
public class Monster {
    private int xPos;
    private int yPos;
    private Value value;

    public Monster(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        value = Value.HIDE_EMPTY;
        //if cheat on then we need to set this to empty.
//        int previousPosition = 4;
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

    public Value getUnder() {
        return value;
    }

    public void setUnder(Value value) {
        this.value = value;
    }

//    public int getPreviousInteger() {
//        return previousPosition;
//    }
//
//    public void setPreviousInteger(int previousPosition) {
//        this.previousPosition = previousPosition;
//    }
}
