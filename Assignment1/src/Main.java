import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A text-based query system of superheros using arraylist.
 * It supports reading from a json file to populate arraylist
 * and writing to a json file. Using a menu, let users to maintain
 * a list of superheros recording a small collection of their attributes.
 * @author Brandon Ha 301333647
 */
public class Main {
    private static final int VALUE_ZERO = 0;
    private static final int VALUE_ONE = 1;
    private static final int VALUE_TWO = 2;
    private static final int VALUE_THREE = 3;
    private static final int VALUE_FOUR = 4;
    private static final int VALUE_FIVE = 5;
    private static final int VALUE_SIX = 6;
    private static final int VALUE_SEVEN = 7;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Superhero> heroes;
        File jsonFile = new File("./hero.json");
        boolean exists = jsonFile.exists();     // check if file exist

        Menu menu = new Menu();
        Scanner myScanner = new Scanner(System.in);

        if (exists && (jsonFile.length() != VALUE_ZERO)) { // Read json file
            Gson gson = new Gson();
            Reader reader = new FileReader("./hero.json");
            Type type = new TypeToken<ArrayList<Superhero>>(){}.getType();
            heroes = gson.fromJson(reader, type);       // Hero object arraylist from json file
        } else {
            heroes = new ArrayList<>();
        }

        menu.display();
        int input = Integer.parseInt(myScanner.nextLine());
        while ((input < VALUE_ONE) || (input > VALUE_SEVEN)) {
            System.out.println("invalid input. Please choose a number from the menu list");
            input = Integer.parseInt(myScanner.nextLine());
        }

        while (input != VALUE_SEVEN) {
            switch (input) {
                case VALUE_ONE -> listHeroes(heroes);
                case VALUE_TWO -> addHero(heroes, myScanner);
                case VALUE_THREE -> removeHero(heroes, myScanner);
                case VALUE_FOUR -> updateSavedCivilians(heroes, myScanner);
                case VALUE_FIVE -> listTopThree(heroes);
                case VALUE_SIX -> listString(heroes);
            }
            menu.display();
            input = Integer.parseInt(myScanner.nextLine());
            while ((input < VALUE_ONE) || (input > VALUE_SEVEN)) {
                System.out.println("invalid input. Please choose a number from the menu list");
                input = Integer.parseInt(myScanner.nextLine());
            }
        }
        write(heroes);
        System.out.println("Thank you for using the system.");
    }

    /**
     * List the heroes and their attributes
     */
    private static void listHeroes(ArrayList<Superhero> heroes) {
        if (heroes.isEmpty()) {
            System.out.println("No heroes present in the list. Add more..");
            System.out.println();
        } else {
            printList(heroes);
        }
    }

    /**
     * add a hero object to the arraylist heroes
     */
    private static void addHero(ArrayList<Superhero> heroes, Scanner myScanner) {
        System.out.println("Enter Hero's name: ");
        String name = myScanner.nextLine();
        System.out.println("Enter Hero's height in cm: ");
        double height = Double.parseDouble(myScanner.nextLine());
        while (height <= VALUE_ZERO){
            System.out.println("invalid number. Enter a positive number greater than zero: ");
            height = Double.parseDouble(myScanner.nextLine());
        }
        System.out.println("Enter Hero's Superpower: ");
        String superpower = myScanner.nextLine();
        heroes.add(new Superhero(name, height, superpower, VALUE_ZERO));
        System.out.println(name + " has been added to the list of superheros.");
        System.out.println();
    }

    /**
     * remove a hero object from the arraylist heroes
     */
    private static void removeHero(ArrayList<Superhero> heroes, Scanner myScanner) {
        printList(heroes);
        System.out.println("Enter Hero number to be removed or Enter 0 to cancel.");
        int input = Integer.parseInt(myScanner.nextLine());
        while((input < VALUE_ZERO) || (input > heroes.size())) {
            System.out.println("Please enter a valid hero number or Enter 0 to cancel.");
            input = Integer.parseInt(myScanner.nextLine());
        }
        if(input > VALUE_ZERO) {
            System.out.println(heroes.get(input - VALUE_ONE).getName() + " has been removed from the list of superheros.");
            heroes.remove(input - VALUE_ONE);
        } else {
            System.out.println("Cancelled hero number removing.");
        }
        System.out.println();
    }

    /**
     * Update a heroes civilian save count
     */
    private static void updateSavedCivilians(ArrayList<Superhero> heroes, Scanner myScanner) {
        printList(heroes);
        System.out.println("Enter Hero number to update civilian count or Enter 0 to cancel");
        int input = Integer.parseInt(myScanner.nextLine());
        while ((input < VALUE_ZERO) || (input > heroes.size())) {
            System.out.println("Please enter a valid hero number to be updated or Enter 0 to cancel.");
            input = Integer.parseInt(myScanner.nextLine());
        }
        if (input > VALUE_ZERO) {
            int j = heroes.get(input - VALUE_ONE).getCivilians();
            System.out.println("enter new civilian save count: ");
            int inputCivilian = Integer.parseInt(myScanner.nextLine());
            while (inputCivilian < VALUE_ZERO) {
                System.out.println("invalid number. Please enter a number.");
                inputCivilian = Integer.parseInt(myScanner.nextLine());
            }
            heroes.get(input - VALUE_ONE).setCivilians(inputCivilian);
            System.out.println("Number of civilians saved by "
                    + heroes.get(input - VALUE_ONE).getName() + " has been updated from "
                    + j + " to " + heroes.get(input - VALUE_ONE).getCivilians());
        } else {
            System.out.println("Cancelled citizen updating.");
        }
        System.out.println();
    }

    /**
     * List the top three heroes who have saved the most civilians.
     * Message if insufficient heroes in the list or
     * not enough civilians saved
     */
    private static void listTopThree(ArrayList<Superhero> heroes) {
        int first, second, third;
        first = second = third = VALUE_ZERO;
        int place1, place2, place3;
        place1 = place2 = place3 = VALUE_ZERO;

        if (heroes.size() < VALUE_THREE) {
            System.out.println("There is not enough superheros in the list. Please add more.");
        } else {
            for (int i = 0; i < heroes.size(); i++) {
                int saved = heroes.get(i).getCivilians();
                if (saved > first) {
                    third = second;
                    second = first;
                    first = saved;
                    place3 = place2;
                    place2 = place1;
                    place1 = i;
                } else if (saved > second) {
                    third = second;
                    second = saved;
                    place3 = place2;
                    place2 = i;
                } else if (saved > third) {
                    third = saved;
                    place3 = i;
                }
            }
            if ((first == VALUE_ZERO) || (second == VALUE_ZERO) || (third == VALUE_ZERO)) {
                System.out.println("The superheros have not saved enough civilians.");
            } else {
                System.out.println("1. " + heroes.get(place1).getName() + " saved " + heroes.get(place1).getCivilians() + " civilians.");
                System.out.println("2. " + heroes.get(place2).getName() + " saved " + heroes.get(place2).getCivilians() + " civilians.");
                System.out.println("3. " + heroes.get(place3).getName() + " saved " + heroes.get(place3).getCivilians() + " civilians.");
            }
        }
        System.out.println();
    }

    /**
     * print out each objects attributes in the arraylist heroes
     */
    private static void listString(ArrayList<Superhero> heroes) {
        for(int i = 0; i < heroes.size(); i++) {
            System.out.println((i + VALUE_ONE) + ". " + heroes.get(i));
        }
        System.out.println();
    }

    /**
     * Writes the objects from the heroes arraylist to a Json file
     */
    private static void write(ArrayList<Superhero> heroes) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("./hero.json")) {
            gson.toJson(heroes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print each superhero information
     */
    private static void printList(ArrayList<Superhero> heroes) {
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + "." + " Hero name: " + heroes.get(i).getName()
                    + ", height: " + heroes.get(i).getHeight() + "cm, "
                    + "superpower: " + heroes.get(i).getSuperpower()
                    + ", saved " + heroes.get(i).getCivilians() + " civilians");
        }
        System.out.println();
    }
}