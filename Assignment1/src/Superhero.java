/**
 * Superhero class models the information about a hero.
 * Information includes hero name, height, superpower
 * and number of civilians saved.
 */
public class Superhero {
    private final String name;
    private final String superpower;
    private final double height;
    private int civilians;

    Superhero(String name, double height, String superpower, int civilians) {
        this.name = name;
        this.superpower = superpower;
        this.height = height;
        this.civilians = civilians;
    }

    public String getName() {
        return name;
    }

    public String getSuperpower() {
        return superpower;
    }

    public double getHeight() {
        return height;
    }

    public int getCivilians() {
        return civilians;
    }

    /**
     * Updates the civilian save count of a chosen hero
     * @param saved is number of civilian to be updated
     */
    public void setCivilians(int saved) {
        civilians = saved;
    }

    /**
     * String representation of object hero on superhero list
     * @return string of hero information
     */
    public String toString() {
        return "MarvelHero{name='" + name + "', heightinCm=" + height
                + ", civilianSaveCount=" + civilians
                + ", superPower='" + superpower + "'}";
    }
}

// IntelliJ makes the methods in one line just need to click the curly bracket to expand