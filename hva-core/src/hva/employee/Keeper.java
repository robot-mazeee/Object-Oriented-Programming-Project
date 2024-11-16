package hva.employee;

import hva.calculator.CalculateKeeperSatisfaction;
import hva.calculator.KeeperCalculator;
import hva.habitat.Habitat;
import java.util.Map;
import java.util.TreeMap;

public class Keeper extends Employee {
    /** All habitats the keeper is reponsible for. */
    private Map<String, Habitat> _habitatsList = new TreeMap<String, Habitat>(String.CASE_INSENSITIVE_ORDER);

    /** Calculator for the keeper's satisfaction. */
    private KeeperCalculator _satisfactionCalculator =
        new CalculateKeeperSatisfaction();

    /**
     * @param id
     * @param name
     */
    public Keeper(String id, String name) {
        super(id, name);
    }

    public int getTreesUnderCare() {
        int treesUnderCare = 0;
        for (Habitat habitat : _habitatsList.values()) {
            treesUnderCare += habitat.getTreeNumber();
        }
        return treesUnderCare;
    }

    public Map<String, Habitat> getHabitatsList() {
        return _habitatsList;
    }

    public Habitat getHabitat(String habitatId) {
        return _habitatsList.get(habitatId);
    }

    public float getSatisfaction() {
        return _satisfactionCalculator.calculate(this);
    }

    /**
     * Visit all the habitats under the keeper's care.
     * @return habitat's ids concatenated.
     */
    public String getHabitatsIds() {
        String habitats = "";
        int numKeys = _habitatsList.keySet().size();
        int currentKey = 0;
        for (String id : _habitatsList.keySet()) {
            currentKey++;
            habitats += id;
            if (currentKey < numKeys) habitats += ",";
        }
        return habitats;
    }

    /**
     * Add habitat to keeper's responsibilities.
     * @param habitat
     */
    public void addHabitat(Habitat habitat) {
        if (_habitatsList.get(habitat.getId()) == null)
            _habitatsList.put(habitat.getId(), habitat);
    }

    public void removeHabitat(Habitat habitat) {
        if (_habitatsList.get(habitat.getId()) != null)
            _habitatsList.remove(habitat.getId());
    }

    @Override
    public String toString() {
        return "TRT|" + super.getId() + "|" + super.getName() + (_habitatsList.isEmpty() ? "" : ("|" + getHabitatsIds()));
    }
}
