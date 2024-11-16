package hva.species;

import java.io.Serializable;

public class Species implements Serializable {
    /** Species' id. */
    private String _id;

    /** Species' name. */
    private String _name;

    /** Number of vets that can vaccinate the species. */
    private int _numberOfVets;

    /** Population of the species. */
    private int _population;

    /**
     * @param id
     * @param name
     */
    public Species(String id, String name) {
        _id = id;
        _name = name;
        _population = 0;
        _numberOfVets = 0;
    }

    /**
     * @return species' id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return species' name.
     */
    public String getName() {
        return _name;
    }

    public int getPopulation() {
        return _population;
    }

    public int getNumberOfVets() {
        return _numberOfVets;
    }
    
    /**
     * Increment species' population.
     */
    public void incrementPopulation() {
        _population++;
    }

    /**
     * Increment number of vets.
     */
    public void incrementNumberOfVets() {
        _numberOfVets++;
    }

    public void decrementNumberOfVets() {
        _numberOfVets--;
    }
}