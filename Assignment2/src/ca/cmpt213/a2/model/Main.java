package ca.cmpt213.a2.model;

import ca.cmpt213.a2.textui.TextUI;

import java.util.*;

/**
 * Maze exploration game that lets the user control a hero
 * to slay monsters in a maze. Players traverse the maze by entering
 * an input direction to search for powers that are used to kill
 * monsters. The class deals with collisions by deciding
 * the proper behaviour depending on next position values.
 * @author Brandon Ha 301333647
 */
public class Main {
    private static final int MAZE_ROW = 15;
    private static final int MAZE_COL = 20;
    private static final int x = 1;
    private static final int y = 1;
    private static int powerCount = 0;
    private static int REMAINING = 3;
    private static int TOTAL_MONSTERS = 3;
    private static int CHEAT_KILL = 3;

    private static final String LEFT = "A";
    private static final String RIGHT = "D";
    private static final String UP = "W";
    private static final String DOWN = "S";

    private static final int LEFT_DIRECTION = 0;
    private static final int RIGHT_DIRECTION = 1;
    private static final int UP_DIRECTION = 2;
    private static final int DOWN_DIRECTION = 3;
    private static String direction;

    private static boolean alive = true;
    private static boolean validInput = false;
    private static boolean rightPlacement = false;

    public static void main(String[] args) {

        Maze maze = new Maze();
        Hero hero = new Hero(x, y);
        Power power = new Power(x, y);
        ArrayList<Monster> monster = new ArrayList<>();
        Scanner myScanner = new Scanner(System.in);

        TextUI.help();
        newMaze(maze);
        setHeroPos(x, y, maze);
        placePower(maze, power);
        setMonsterPos(monster, maze);
        showSurrounding(maze, hero);
        showMaze(maze);
        details();
        while ((alive) && (REMAINING > 0) && (CHEAT_KILL > 0)) {
            direction = inputDirection(myScanner, maze, monster, hero);
            checkCollision(maze, hero, direction, monster, power);
            showSurrounding(maze, hero);
            showMaze(maze);
            details();
            if((alive) && (REMAINING != 0) && (CHEAT_KILL != 0)) {
                TextUI.enter();
            }
        }
        if(!alive) {
            TextUI.eaten();
        } else if ((REMAINING == 0) || (CHEAT_KILL == 0)) {
            TextUI.win();
        }
    }

    /**
     * Displays the area surrounding
     * the hero player
     */
    private static void showSurrounding(Maze maze, Hero hero) {
        int x = hero.getX();
        int y = hero.getY();

        showCell(maze, x, y);
        showCell(maze, x - 1, y);
        showCell(maze, x + 1, y);
        showCell(maze, x, y - 1);
        showCell(maze, x, y + 1);
        showCell(maze,x - 1, y + 1);
        showCell(maze,x + 1, y - 1);
        showCell(maze,x - 1, y - 1);
        showCell(maze, x + 1, y + 1);
    }

    /**
     * Shows the hero player's surrounding
     * depending on x and y hero position
     */
    private static void showCell(Maze maze, int x, int y) {
        Value show = maze.getMazeCell(x, y);
        if (show == Value.HIDE_EMPTY) {
            maze.setMazeCell(x, y, Value.EMPTY_SPACE);
        } else if (show == Value.HIDE_WALL) {
            maze.setMazeCell(x, y, Value.WALL);
        }
    }

    /**
     * Displays the maze
     */
    private static void showMaze(Maze maze) {
        TextUI.mazeName();
        TextUI.fillMaze(maze.getMaze());
    }

    /**
     * hides the maze cells that are occupied by an empty space or wall.
     * This acts as not-yet revealed cells to the user.
     */
    private static void newMaze(Maze maze) {
        for (int i = 1; i < MAZE_ROW - 1; i++) {
            for (int j = 1; j < MAZE_COL - 1; j++) {
                Value hide = maze.getMazeCell(i, j);
                if (hide == Value.EMPTY_SPACE) {
                    maze.setMazeCell(i, j, Value.HIDE_EMPTY);
                } else if (hide == Value.WALL) {
                    maze.setMazeCell(i, j, Value.HIDE_WALL);
                }
            }
        }
    }

    /**
     * lets the player see the whole maze by revealing all hidden areas
     */
    private static void mapReveal(Maze maze) {
        for (int i = 1; i < MAZE_ROW - 1; i++) {
            for (int j = 1; j < MAZE_COL - 1; j++) {
                Value reveal = maze.getMazeCell(i, j);
                if (reveal == Value.HIDE_EMPTY) {
                    maze.setMazeCell(i, j, Value.EMPTY_SPACE);
                } else if (reveal == Value.HIDE_WALL) {
                    maze.setMazeCell(i, j, Value.WALL);
                }
            }
        }
    }

    /**
     * This checks the collisions between objects and cells in the maze.
     * It takes in a direction argument that makes appropriate decisions with
     * the hero and monster when handling collisions.
     */
    private static void checkCollision(Maze maze, Hero hero, String direction, ArrayList<Monster> monster, Power power) {
        Value nextMonsterPos = null;
        Value nextHeroPos = nextHeroPos(maze, hero, direction);
        if ((nextHeroPos.equals(Value.EMPTY_SPACE)) || (nextHeroPos.equals(Value.POWER)) || (nextHeroPos.equals(Value.MONSTER))) {
            maze.getMaze()[hero.getX()][hero.getY()].setValue(Value.EMPTY_SPACE);
            switch (direction) {
                case LEFT -> hero.moveLeft();
                case (RIGHT) -> hero.moveRight();
                case UP -> hero.moveUp();
                case DOWN -> hero.moveDown();
            }
            if (nextHeroPos.equals(Value.MONSTER)) {
                int a = 0;
                int b = monster.size();
                int i = 0;
                while (a < b && alive) {
                    if ((monster.get(i).getX() == hero.getX()) && (monster.get(i).getY() == hero.getY())) {
                        powerCount--;
                        REMAINING--;
                        CHEAT_KILL--;

                        if ((powerCount >= 0) && (monster.get(i).getX() == power.getX()) && (monster.get(i).getY() == power.getY())) {
                            maze.getMaze()[power.getX()][power.getY()].setValue(Value.POWER);
                            nextHeroPos = maze.getMaze()[power.getX()][power.getY()].getValue();
                        }
                        monster.remove(i);
                    } else {
                        i++;
                    }
                    if (powerCount < 0) { //lose
                        alive = false;
                    }
                    a++;
                }
            }
            if (nextHeroPos.equals((Value.POWER))) { //gain power/random power location
                rightPlacement = false;
                placePower(maze, power);
                powerCount++;
                if(REMAINING == 0) {
                    powerCount--;
                }
            }

            setHeroPos(hero.getX(), hero.getY(), maze); //set hero new position
            if (powerCount < 0) { //lose
                maze.getMaze()[hero.getX()][hero.getY()].setValue(Value.DEAD);
                alive = false;
            }
        }
        int a = 0;
        int b = monster.size();
        int i = 0;
        while (a < b && alive) {
            int r = 0;
            ListIterator<Integer> randomOrder = monsterRandomDirection().listIterator();
            boolean notWall = false;
            while ((randomOrder.hasNext()) && (!notWall)) {
                r = randomOrder.next();
                if (r == LEFT_DIRECTION) {
                    nextMonsterPos = maze.getMaze()[monster.get(i).getX()][monster.get(i).getY() - 1].getValue();
                } else if (r == RIGHT_DIRECTION) {
                    nextMonsterPos = maze.getMaze()[monster.get(i).getX()][monster.get(i).getY() + 1].getValue();
                } else if (r == UP_DIRECTION) {
                    nextMonsterPos = maze.getMaze()[monster.get(i).getX() - 1][monster.get(i).getY()].getValue();
                } else if (r == DOWN_DIRECTION) {
                    nextMonsterPos = maze.getMaze()[monster.get(i).getX() + 1][monster.get(i).getY()].getValue();
                }
                notWall = (nextMonsterPos != Value.WALL) && (nextMonsterPos != Value.HIDE_WALL);
            }

            if (monster.get(i).getUnder() == Value.HIDE_EMPTY) {
                maze.getMaze()[monster.get(i).getX()][monster.get(i).getY()].setValue(Value.HIDE_EMPTY);
            } else {
                maze.getMaze()[monster.get(i).getX()][monster.get(i).getY()].setValue(Value.EMPTY_SPACE);
            }
            if (monster.get(i).getX() == power.getX() && monster.get(i).getY() == power.getY()) {
                maze.getMaze()[power.getX()][power.getY()].setValue(Value.POWER);
            }

            if (r == LEFT_DIRECTION) {
                monster.get(i).moveLeft();
            } else if (r == RIGHT_DIRECTION) {
                monster.get(i).moveRight();
            } else if (r == UP_DIRECTION) {
                monster.get(i).moveUp();
            } else if (r == DOWN_DIRECTION) {
                monster.get(i).moveDown();
            }
            if (maze.getMaze()[monster.get(i).getX()][monster.get(i).getY()].getValue() == Value.EMPTY_SPACE) {
                monster.get(i).setUnder(Value.EMPTY_SPACE);
            } else if (maze.getMaze()[monster.get(i).getX()][monster.get(i).getY()].getValue() == Value.HIDE_EMPTY) {
                monster.get(i).setUnder(Value.HIDE_EMPTY);
            }
            assert nextMonsterPos != null;
            if (nextMonsterPos.equals(Value.HERO)) {
                powerCount--;
                if(powerCount >= 0) {
                    REMAINING--;
                    monster.remove(i);
                }
            } else {
                i++;
            }
            if (powerCount < 0) { //lose
                powerCount++;
                alive = false;
            }
            a++;
        }

        if (!monster.isEmpty()) {
            for (Monster value : monster) {
                maze.getMaze()[value.getX()][value.getY()].setValue(Value.MONSTER);
            }
        }
        if (!alive) {
            maze.getMaze()[hero.getX()][hero.getY()].setValue(Value.DEAD);
        }
    }

    /**
     * random order of numbers from 0 to 3.
     * Numbers represent random order of directions
     */
    private static ArrayList<Integer> monsterRandomDirection() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    /**
     * sets cell value to hero.
     */
    private static void setHeroPos(int i, int j, Maze m) {
        m.getMaze()[i][j].setValue(Value.HERO);
    }

    /**
     * next hero position value
     */
    private static Value nextHeroPos(Maze maze, Hero hero, String direction) {
        Value nextPos = null;
        if (LEFT.equals(direction)) {
            nextPos = maze.getMaze()[hero.getX()][hero.getY() - 1].getValue();
        } else if (RIGHT.equals(direction)) {
            nextPos = maze.getMaze()[hero.getX()][hero.getY() + 1].getValue();
        } else if (UP.equals(direction)) {
            nextPos = maze.getMaze()[hero.getX() - 1][hero.getY()].getValue();
        } else if (DOWN.equals(direction)) {
            nextPos = maze.getMaze()[hero.getX() + 1][hero.getY()].getValue();
        }
        return nextPos;
    }

    /**
     * add monster objects to an arraylist and set the
     * objects position to a MONSTER cell value.
     */
    private static void setMonsterPos(ArrayList<Monster> monster, Maze maze) {
        monster.add(new Monster(1, MAZE_COL - 2));
        monster.add(new Monster(MAZE_ROW - 2, 1));
        monster.add(new Monster(MAZE_ROW - 2, MAZE_COL - 2));
        for (Monster pos : monster) {
            maze.getMaze()[pos.getX()][pos.getY()].setValue(Value.MONSTER);
        }
    }

    /**
     * Places a power in the maze that is
     * not occupied by a hero or wall.
     */
    private static void placePower(Maze maze, Power power) {
        int randomX;
        int randomY;
        Value value;
        while (!rightPlacement) {
            randomX = randomPositionX();
            randomY = randomPositionY();
            value = maze.getMaze()[randomX][randomY].getValue();
            if ((!value.equals(Value.WALL)) && (!value.equals(Value.HERO)) && (!value.equals(Value.HIDE_WALL))) {
                power.setX(randomX);
                power.setY(randomY);
                maze.getMaze()[randomX][randomY].setValue(Value.POWER);
                rightPlacement = true;
            } else {
                rightPlacement = false;
            }
        }
    }

    /**
     * Shows the maze details including monsters in the maze,
     * number of powers in possession and monsters remaining
     */
    private static void details() {
        if(powerCount == -1) {
            powerCount++;
            REMAINING++;
        }
        TextUI.monsters();
        TextUI.possession();
        TextUI.monstersRemaining();
    }

    /**
     * generates a random number for the x position
     */
    private static int randomPositionX() {
        Random rand = new Random();
        return rand.nextInt(MAZE_ROW - 2) + 1;
    }

    /**
     * generates a random number for the y position
     */
    private static int randomPositionY() {
        Random rand = new Random();
        return rand.nextInt(MAZE_COL - 2) + 1;
    }

    /**
     * Takes in a user input and checks if it is a valid input or not
     */
    private static String inputDirection(Scanner myScanner, Maze maze, ArrayList<Monster> monster, Hero hero) {
        validInput = false;
        while (!validInput) {
            direction = userInput(myScanner);
            validateMovement(direction, maze, monster, hero);
        }
        return direction;
    }

    /**
     * returns the user input as a capital letter
     */
    private static String userInput(Scanner myScanner) {
        return myScanner.nextLine().toUpperCase();
    }

    /**
     * checks the user input. Depending on user input, it could display
     * a help menu, reveal maze cells, lower kill count
     */
    private static void validateMovement(String direction, Maze maze, ArrayList<Monster> monster, Hero hero) {
        boolean validate = (direction.equals(LEFT)) || (direction.equals(RIGHT)) || (direction.equals(UP)) || (direction.equals(DOWN));
        if (validate) {
            validInput = true;
            Value check = nextHeroPos(maze, hero, direction);
            if(check == Value.WALL) {
                TextUI.wall();
                TextUI.enter();
                validInput = false;
            }
        } else if (direction.equals("M")) {
            mapReveal(maze);
            for (Monster monsters: monster) {
                monsters.setUnder(Value.EMPTY_SPACE);
            }
            showMaze(maze);
            details();
            TextUI.enter();
            validInput = false;
        } else if (direction.equals("?")) {
            TextUI.help();
            TextUI.enter();
            validInput = false;
        } else if (direction.equals("C")) {
            TOTAL_MONSTERS = TOTAL_MONSTERS - 2;
            CHEAT_KILL = CHEAT_KILL - 2;
            TextUI.enter();
            validInput = false;
        } else {
            TextUI.invalidInput();
            validInput = false;
        }
    }

    public static int getPowerCount() {
        return powerCount;
    }

    public static int getRemaining() {
        return REMAINING;
    }

    public static int getTotalNeededKills() {
        return TOTAL_MONSTERS;
    }
}