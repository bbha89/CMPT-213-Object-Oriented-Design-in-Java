package ca.cmpt213.a3.shapes;

/**
 * Concrete class to draw the rectangle shape
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class Rectangle extends ShapeImp1 {
    public Rectangle(int locationX, int locationY, int width, int height) {
        super(locationX, locationY, width, height);
    }

    /**
     *Set the rectangle shape border indices to true
     */
    @Override
    protected boolean isBorder(int xPos, int yPos) {
        if (xPos == 0 || xPos == getWidth() - 1) {
            return true;
        } else return yPos == 0 || yPos == getHeight() - 1;
    }

    /**
     *Set the indices inside the rectangle to true
     */
    @Override
    protected boolean isInside(int xPos, int yPos) {
        return ((xPos >= 0) && (xPos <= getWidth() - 1) && ((yPos >= 0) && (yPos <= getHeight() - 1)));
    }


}