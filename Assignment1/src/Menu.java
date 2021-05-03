/**
 * Displays the title's menu and menu options for users
 */
public class Menu {
    private static final String[] options = {"List all superheroes", "Add a new superhero",
            "Remove a superhero", "Update number of civilians saved by a superhero",
            "List Top 3 superheroes", "Debug Dump (toString)", "Exit"
    };

    private static final String title = "SuperHero Tracker";

    /**
     * Displays title and menu options
     */
    public void display() {
        for (int i = 0; i < title.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println(title);
        for (int i = 0; i < title.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ":" + options[i]);
        }
        System.out.println("Enter: ");
    }
}