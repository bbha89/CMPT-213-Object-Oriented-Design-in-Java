package ca.cmpt213.a2.model;

/**
 * Sets a cell index's value and
 * checks if it is visited or not
 */
public class Cell {
    private boolean visited;
    private Value value;
    private final int x;
    private final int y;

    public Cell(int i, int j) {
        visited = false;
        value = Value.WALL;
        this.x = i;
        this.y = j;
    }

    public Value getValue() {
        return value;
    }

    public boolean getVisited() {
        return !visited;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setVisited() {
        visited = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
