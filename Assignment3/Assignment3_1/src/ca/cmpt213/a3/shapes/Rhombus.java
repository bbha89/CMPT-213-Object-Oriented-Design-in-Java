package ca.cmpt213.a3.shapes;

/**
 * Concrete class to draw the Rhombus shape
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class Rhombus extends ShapeImp1{
    public Rhombus(int x, int y, int size) {
        super(x, y, size*2-1, size*2-1);
    }

    /**
     *Set the shape border indices to true
     */
    @Override
    protected boolean isBorder(int x, int y) {
        int getSideLength = getWidth()/2;
        return (getSideLength == y + x) || (getSideLength == y - x)
                || (getSideLength == x - y) || (getSideLength + getWidth() - 1 == x + y);
    }

    /**
     *Set the indices inside the rhombus to true
     */
    @Override
    protected boolean isInside(int x, int y) {
        int getSideLength = getWidth()/2;
        return ((y + x) > getSideLength) && ((y - x) < getSideLength)
                && ((x - y) < getSideLength) && ((x + y) < (getSideLength + getWidth() - 1));
    }
}
