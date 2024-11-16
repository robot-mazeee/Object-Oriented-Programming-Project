package hva.habitat;
import java.util.Map;

import hva.animal.Animal;
import hva.species.Species;
import hva.tree.Tree;

import java.io.Serializable;
import java.util.TreeMap;

public class Habitat implements Serializable {
    /** The habitat's id. */
    private String _id;

    /** The habitat's name. */
    private String _name;

    /** Species' adequacy to the habitat. */
    private Map<String, Integer> _adequacy = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);

    /** Area of the habitat. */
    private int _area;

    private int _numberOfKeepers;

    /** Animals in the habitat. */
    private Map<String, Animal> _animalsList = new TreeMap<String, Animal>(String.CASE_INSENSITIVE_ORDER);

    /** Trees in the habitat. */
    private Map<String, Tree> _treesList = new TreeMap<String, Tree>(String.CASE_INSENSITIVE_ORDER);

    /**
     * @param id
     * @param name
     * @param area
     */
    public Habitat(String id, String name, int area) {
        _id = id;
        _name = name;
        _area = area;
    }

    /**
     * @return habitat's id.
     */
    public String getId() {
        return _id;
    }

    public int getArea() {
        return _area;
    }

    public int getPopulation() {
        return _animalsList.size();
    }

    /**
     * @return number of trees in the habitat.
     */
    public int getTreeNumber() {
        return _treesList.size();
    }

    public int getAdequacy(Species species) {
        return _adequacy.get(species.getId());
    }

    public int getNumberOfKeepers() {
        return _numberOfKeepers;
    }

    public Map<String, Animal> getAnimalsList() {
        return _animalsList;
    }

    public Map<String, Tree> getTreesList() {
        return _treesList;
    }

    /**
     * @return all trees in habitat.
     */
    public String getAllTreesInHabitat() {
        String allTrees = "";
        for (Tree t : _treesList.values()) {
            allTrees += "\n" + t;
        }
        return _treesList.isEmpty() ? "" : allTrees + "\n";
    }

    public String getAllAnimalsInHabitat() {
        String allAnimals = "";
        for (Animal a : _animalsList.values()) {
            allAnimals += "\n" + a;
        }
        return _animalsList.isEmpty() ? "" : allAnimals + "\n";
    }

    public void setArea(int area) {
        _area = area;
    }

    public void incrementNumberOfKeepers() {
        _numberOfKeepers++;
    }

    public void decrementNumberOfKeepers() {
        _numberOfKeepers--;
    }

    /**
     * Add a tree to the habitat.
     * @param tree tree to add.
     */
    public void addTree(Tree tree) {
        _treesList.put(tree.getId(), tree);
    }

    /**
     * Add an animal to the habitat.
     * @param animal animal to add.
     */
    public void addAnimal(Animal animal) {
        _animalsList.put(animal.getId(), animal);
    }

    public void removeAnimal(Animal animal) {
        _animalsList.remove(animal.getId());
    }

    public void addSpecies(String speciesId) {
        _adequacy.put(speciesId, 0);
    }

    public void changeHabitatInfluence(String speciesId, Influence influence) {
        _adequacy.put(speciesId, influence.getValue());
    }

    @Override
    public String toString() {
        return "HABITAT|" + _id + "|" + _name + "|" + _area + "|" + getTreeNumber() + getAllTreesInHabitat();
    }
}