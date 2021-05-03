package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Shows the maze that the user would
 * see generated on the screen using
 * recursive backtracker implementing DFS
 * algorithm using a stack. This class also
 * removes walls for additional paths through
 * the maze.
 */
public class Maze {
    private static final int MAZE_ROW = 15;
    private static final int MAZE_COL = 20;

    private static final int LEFT = 3;
    private static final int RIGHT = 0;
    private static final int UP = 2;
    private static final int DOWN = 1;

    private static final int REMOVE_SOME_WALLS = 25;

    public Cell[][] maze;
    int xPos = 1;
    int yPos = 1;

    public Maze() {
        maze = new Cell[MAZE_ROW][MAZE_COL];
        createMaze(maze);
        setBorder(maze);
        depthFirstSearch(maze, xPos, yPos);
        removeInnerWalls(maze);
    }

    /**
     * remove walls in the corners of the maze with a path
     */
    private void removeCornerWalls() {
        maze[1][1].setValue(Value.EMPTY_SPACE);
        maze[1][1].setVisited();
        maze[1][MAZE_COL-2].setValue(Value.EMPTY_SPACE);
        maze[1][MAZE_COL-2].setVisited();
        maze[MAZE_ROW-2][MAZE_COL-2].setValue(Value.EMPTY_SPACE);
        maze[MAZE_ROW-2][MAZE_COL-2].setVisited();
        maze[MAZE_ROW-2][1].setValue(Value.EMPTY_SPACE);
        maze[MAZE_ROW-2][1].setVisited();
    }

    /**
     * remove extra walls to create more paths
     */
    private void removeInnerWalls(Cell[][] maze) {
        removeCornerWalls();
        checkSurroundingSpace(maze);
        checkSideWall(maze);
    }

    /**
     * removes extra walls on the right side of the maze
     */
    private void checkSideWall(Cell[][] maze) {
        for (int i = 2; i < MAZE_ROW - 3; i ++) {
            if ((maze[i - 1][MAZE_COL-2].getValue() != Value.EMPTY_SPACE ||
            maze[i][MAZE_COL - 3].getValue() != Value.EMPTY_SPACE ||
            maze[i - 1][MAZE_COL - 3].getValue() != Value.EMPTY_SPACE) &&
                    (maze[i][MAZE_COL - 3].getValue() != Value.EMPTY_SPACE ||
            maze[i + 1][MAZE_COL - 2].getValue() != Value.EMPTY_SPACE ||
            maze[i + 1][MAZE_COL - 3].getValue() != Value.EMPTY_SPACE)) {
                maze[i][MAZE_COL - 2].setValue(Value.EMPTY_SPACE);
            }
        }
    }

    /**
     * removes inner walls randomly in the maze. This method checks to see
     * if a wall can be replaced with an empty cell value by ensuring to only place
     * an empty cell if the surrounding empty cells do not form a 2x2 square of empty cells.
     */
    private static void checkSurroundingSpace(Cell[][] maze) {
        Random rand = new Random();
        int wallRemoval = REMOVE_SOME_WALLS;
        while (wallRemoval > 0) {
            int randomNumberX = rand.nextInt(MAZE_ROW - 2) + 1;
            int randomNumberY = rand.nextInt(MAZE_COL - 2) + 1;
            if (maze[randomNumberX][randomNumberY].getValue() == Value.WALL) {
                if ((maze[randomNumberX-1][randomNumberY].getValue() != Value.EMPTY_SPACE ||
                        maze[randomNumberX][randomNumberY-1].getValue() != Value.EMPTY_SPACE ||
                        maze[randomNumberX-1][randomNumberY-1].getValue() != Value.EMPTY_SPACE) &&
                        (maze[randomNumberX - 1][randomNumberY].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX][randomNumberY + 1].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX - 1][randomNumberY + 1].getValue() != Value.EMPTY_SPACE) &&
                        (maze[randomNumberX + 1][randomNumberY].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX][randomNumberY - 1].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX + 1][randomNumberY - 1].getValue() != Value.EMPTY_SPACE) &&
                        (maze[randomNumberX + 1][randomNumberY].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX][randomNumberY + 1].getValue() != Value.EMPTY_SPACE ||
                                maze[randomNumberX + 1][randomNumberY + 1].getValue() != Value.EMPTY_SPACE)) {
                    maze[randomNumberX][randomNumberY].setValue(Value.EMPTY_SPACE);
                    wallRemoval--;
                }
            }
        }
    }

    /**
     * Set 2-d maze index to the cell objects value
     */
    private static void createMaze(Cell[][] maze) {
        for (int i = 0; i < MAZE_ROW; i++) {
            for (int j = 0; j < MAZE_COL; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Create the outer walls
     */
    private static void setBorder(Cell[][] maze) {
        for (int x = 0; x < MAZE_COL; x++) {
            maze[0][x].setVisited();
            maze[MAZE_ROW - 1][x].setVisited();
        }
        for (int y = 1; y < MAZE_ROW; y++) {
            maze[y][0].setVisited();
            maze[y][MAZE_COL - 1].setVisited();
        }
    }

    /**
     * random order of numbers from 0 to 3
     */
    private ArrayList<Integer> randomDirection() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    /**
     * Performs the depth first search algorithm to generate a random maze. It uses
     * recursive backtracking and a stack to implement this algorithm.
     * Depending on a random direction, the algorithm will check if a cell has been
     * visited if not, then it will create a path and remove the wall between the cells.
     */
    private void depthFirstSearch(Cell[][] maze, int x, int y) {
        boolean path;
        int direction = 1;
        Stack<Cell> stack = new Stack<>();
        Cell temp = new Cell(x, y);
        stack.push(temp);
        ArrayList<Integer> list;
        while (!stack.empty()) {
            path = false;
            int allDirections = 0;
            Cell s = stack.peek();
            stack.pop();
            x = s.getX();
            y = s.getY();

            list = randomDirection();
            while (!path) {
                if (allDirections < 4) {
                    direction = list.get(allDirections);
                } else {
                    path = true;
                }
                switch (direction) {
                    case RIGHT:    //right
                        if (y + 2 < MAZE_COL && maze[s.getX()][s.getY() + 2].getVisited()) {
                            path = true;
                            stack.push(s);
                            maze[x][y + 1].setVisited();
                            maze[x][y + 1].setValue(Value.EMPTY_SPACE);
                            maze[x][y + 2].setVisited();
                            maze[x][y + 2].setValue(Value.EMPTY_SPACE);
                            Cell temp2 = new Cell(x, y + 2);
                            stack.push(temp2);
                        }
                        break;
                    case DOWN:     //down
                        if (x + 2 < MAZE_ROW && maze[s.getX() + 2][s.getY()].getVisited()) {
                            path = true;
                            stack.push(s);
                            maze[x + 1][y].setVisited();
                            maze[x + 1][y].setValue(Value.EMPTY_SPACE);
                            maze[x + 2][y].setVisited();
                            maze[x + 2][y].setValue(Value.EMPTY_SPACE);
                            Cell temp4 = new Cell(x + 2, y);
                            stack.push(temp4);
                        }
                        break;
                    case LEFT:    //left
                        if (s.getY() - 2 >= 0 && maze[s.getX()][s.getY() - 2].getVisited()) {
                            path = true;
                            stack.push(s);
                            x = s.getX();
                            y = s.getY();
                            maze[x][y - 1].setVisited();
                            maze[x][y - 1].setValue(Value.EMPTY_SPACE);
                            maze[x][y - 2].setVisited();
                            maze[x][y - 2].setValue(Value.EMPTY_SPACE);
                            Cell temp1 = new Cell(x, y - 2);
                            stack.push(temp1);
                        }
                        break;
                    case UP:       //up
                        if (x - 2 >= 0 && maze[s.getX() - 2][s.getY()].getVisited()) {
                            path = true;
                            stack.push(s);
                            maze[x - 1][y].setVisited();
                            maze[x - 1][y].setValue(Value.EMPTY_SPACE);
                            maze[x - 2][y].setVisited();
                            maze[x - 2][y].setValue(Value.EMPTY_SPACE);
                            Cell temp3 = new Cell(x - 2, y);
                            stack.push(temp3);
                        }
                        break;
                }
                allDirections++;
            }
        }
    }

    /**
     * return a copy of the maze
     */
    public Cell[][] getMaze() {
        Cell[][] mazeCopy = new Cell[MAZE_ROW][MAZE_COL];
        for (int i = 0; i < MAZE_ROW;i ++){
            System.arraycopy(maze[i], 0, mazeCopy[i], 0, MAZE_COL);
        }
        return mazeCopy;
    }

    /**
     * Change the cell value of an index
     */
    public void setMazeCell( int x, int y, Value value) {
        maze[x][y].setValue(value);
    }

    /**
     * get a cell value at an index
     */
    public Value getMazeCell (int x, int y) {
        return maze[x][y].getValue();
    }

}
