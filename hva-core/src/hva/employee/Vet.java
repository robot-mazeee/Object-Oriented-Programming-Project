package hva.employee;
import hva.species.Species;
import hva.vaccine.Vaccination;
import hva.calculator.CalculateVetSatisfaction;
import hva.calculator.VetCalculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Vet extends Employee {
    /** All the species the vet can vaccinate. */
    private Map<String, Species> _species = new TreeMap<String, Species>(String.CASE_INSENSITIVE_ORDER);

    /** List of medical act's done by vet. */
    private List<Vaccination> _medicalActs = new ArrayList<>();

    /** Calculator for the vet's satisfaction. */
    private VetCalculator _satisfactionCalculator = 
        new CalculateVetSatisfaction(); 

    /**
     * @param id
     * @param name
     */
    public Vet(String id, String name){
        super(id, name);
    }

    public Species getSpecies(String speciesId) {
        return _species.get(speciesId);
    }

    public Map<String, Species> getAllSpecies() {
        return _species;
    }

    public float getSatisfaction() {
        return _satisfactionCalculator.calculate(this);
    }

    /**
     * Visit all species the vet can vaccinate.
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
     * Adds species to the vet's care.
     * @param species
     */
    public void addSpecies(Species species) {
        if (_species.get(species.getId()) == null)
            _species.put(species.getId(), species);
    }

    public void removeSpecies(Species species) {
        if (_species.get(species.getId()) != null)
            _species.remove(species.getId());
    }

    public void addMedicalAct(Vaccination vaccination) {
        _medicalActs.add(vaccination);
    }
    
    public String getVetMedicalActs() {
        String medicalActs = "";
        for (Vaccination vaccination : _medicalActs) {
            medicalActs += "\n" + vaccination;
        }
        return _medicalActs.isEmpty() ? "" : medicalActs + "\n";
    }

    @Override
    public String toString() {
        return "VET|" + super.getId() + "|" + super.getName() + (_species.isEmpty() ? "" : ("|" + getSpeciesIds()));
    }
}