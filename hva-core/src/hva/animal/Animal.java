package hva.animal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hva.calculator.AnimalCalculator;
import hva.calculator.CalculateAnimalSatisfaction;
import hva.habitat.Habitat;
import hva.species.Species;
import hva.vaccine.Vaccination;

public class Animal implements Serializable {
    /** The animal's id. */
    private String _id;

    /** The animal's name */
    private String _name;

    /** The animal's species. */
    private Species _species;

    /** The animal's health history. */
    private String _health = "";

    /** The animal's vaccination history. */
    private List<Vaccination> _medicalActs = new ArrayList<>();

    /** Calculator for the animal's satisfaction. */
    private AnimalCalculator _satisfactionCalculator = 
        new CalculateAnimalSatisfaction();

    /** The animal's habitat. */
    private Habitat _habitat;

    /** 
     * @param id
     * @param name
     * @param species
     * @param habitat
     */
    public Animal(String id, String name, Species species, Habitat habitat) {
        _id = id;
        _name = name;
        _species = species;
        _habitat = habitat;
    }

    /**
     * @return the animal's id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the animal's species' id.
     */
    public String getSpeciesId() {
        return _species.getId();
    }

    /**
     * @return the animal's habitat's id.
     */
    public String getHabitatId() {
        return _habitat.getId();
    }

    public float getSatisfaction() {
        float satisfaction = _satisfactionCalculator.calculate(this);
        return satisfaction;
    }

    public Species getSpecies() {
        return _species;
    }

    public Habitat getHabitat() {
        return _habitat;
    }

    public void setHabitat(Habitat newHabitat) {
        _habitat = newHabitat;
    }

    public void receiveVaccination(Vaccination vaccination) {
        if (_health.length() != 0)
            _health += ',';
        _health += vaccination.getDamage();
        _medicalActs.add(vaccination);
    }

    public String getAnimalMedicalActs() {
        String medicalActs = "";
        for (Vaccination vaccination : _medicalActs) {
            medicalActs += "\n" + vaccination;
        }
        return _medicalActs.isEmpty() ? "" : medicalActs + "\n";
    }

    @Override
    public String toString() {
        return "ANIMAL|" + _id + "|" + _name + "|" + getSpeciesId() + "|" + (_health.equals("") ? "VOID" : _health) + "|" + getHabitatId();
    }
}
