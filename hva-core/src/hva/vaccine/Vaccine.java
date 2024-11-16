package hva.vaccine;

import hva.species.Species;
import java.util.TreeMap;
import java.util.Map;
import java.io.Serializable;

public class Vaccine implements Serializable {
    /** Vaccine's id. */
    private String _id;

    /** Vaccine's name. */
    private String _name;

    /** Species the vaccine can be applied to. */
    private Map<String, Species> _species = 
        new TreeMap<String, Species>(String.CASE_INSENSITIVE_ORDER);
    
    /** Vaccine's current number of applications. */
    private int _numberOfApplications = 0;

    /**
     * @param id
     * @param name
     */
    public Vaccine(String id, String name) {
        _id = id;
        _name = name;
    }

    /**
     * @return vaccine's id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return number of times the vaccine was applied.
     */
    public int getNumberOfApplications() {
        return _numberOfApplications;
    }

    public void incrementNumberOfApplications(){
        _numberOfApplications += 1;
    }

    public Map<String, Species> getSpeciesList(){
        return _species;
    }

    /**
     * Visit species.
     * @return species' ids concatenated.
     */
    public String getSpeciesIds() {
        String species = "";
        int numKeys = _species.keySet().size();
        int currentKey = 0;
        for (String id : _species.keySet()) {
            currentKey++;
            species += id;
            if (currentKey < numKeys) species += ",";
        }
        return species;
    }

    /**
     * Add species to list.
     * @param species
     */
    public void addSpecies(Species species) {
        _species.put(species.getId(), species);
    }

    @Override
    public String toString() {
        return "VACINA|" + _id + "|" + _name + "|" + getNumberOfApplications() + (_species.isEmpty() ? "" : ("|" + getSpeciesIds()));
    }
}