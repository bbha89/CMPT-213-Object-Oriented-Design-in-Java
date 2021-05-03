package ca.cmpt213.a3.onlinesuperherotracker.model;

/**
 * Superhero class models the information about a hero.
 * Information includes hero name, height, superPower,
 * ID, and number of civilians saved.
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public class Superhero {
    private long id;
    private String name;
    private double heightInCm;
    private int civilianSaveCount;
    private String superPower;

    public Superhero() {
    }

    public Superhero(long id, String name, double heightInCm, int civilianSaveCount, String superPower) {
        this.name = name;
        this.superPower = superPower;
        this.heightInCm = heightInCm;
        this.civilianSaveCount = civilianSaveCount;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSuperPower() {
        return superPower;
    }

    public double getHeightInCm() {
        return heightInCm;
    }

    public int getCivilianSaveCount() {
        return civilianSaveCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}