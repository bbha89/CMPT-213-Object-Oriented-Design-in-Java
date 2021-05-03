package ca.cmpt213.a2.textui;

import ca.cmpt213.a2.model.Cell;
import ca.cmpt213.a2.model.Value;

/**
 * Displays the elements of each cell
 * on the maze and all the text that
 * the user will see
 */
public class TextUI {
    private static final int MAZE_ROW = 15;
    private static final int MAZE_COL = 20;

    private static final String directions = "DIRECTIONS: ";
    private static final String legend = "LEGEND: ";
    private static final String moves = "MOVES: ";
    private static final String name = "Maze:";

    public static void fill(Cell[][] maze) {
        for (int i = 0; i < MAZE_ROW; i++) {
            for (int j = 0; j < MAZE_COL; j++) {
                Value cellValue = maze[i][j].getValue();
                switch (cellValue) {
                    case WALL -> System.out.print("#");
                    case EMPTY_SPACE -> System.out.print(" ");
                    case HERO -> System.out.print("@");
                    case MONSTER -> System.out.print("!");
                    case POWER -> System.out.print("$");
                    case DEAD -> System.out.print("X");
                    case HIDE_WALL, HIDE_EMPTY -> System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public static void invalidInput() {
        System.out.println("invalid move. Please enter just A (left), S (down), D (right), W (up).");
    }

    public static void help() {
        System.out.println(directions);
        System.out.println("\tKill 3 Monsters!");
        System.out.println(legend);
        System.out.println("\t#: Wall");
        System.out.println("\t@: You (a hero)");
        System.out.println("\t!: Monster");
        System.out.println("\t$: Power");
        System.out.println("\t.: Unexplored space");
        System.out.println(moves);
        System.out.println("\tUse W (up), A (left), S (down) and D (right) to move.");
        System.out.println("\tYou must press enter after each move.");
    }

    public static void mazeName() {
        System.out.println();
        System.out.println(name);
    }

    public static void eaten() {
        System.out.println("I'm sorry, you have been eaten!");
    }

    public static void win() {
        System.out.println("You have killed all monsters and won the game!");
    }

    public static void wall() {
        System.out.println("Invalid move: you cannot move through walls!");
    }

    public static String monsters() {
        return "Total number of monsters to be killed: ";
    }
    public static String possession() {
         return "Number of powers currently in possession: ";
    }

    public static String monstersRemaining() {
        return "Number of monsters alive: ";
    }

    public static void enter() {
        System.out.println("Enter your move [WASD?] : ");
    }

}
