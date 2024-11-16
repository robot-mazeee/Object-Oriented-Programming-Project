package hva.calculator;

import java.util.Map;

import hva.employee.Keeper;
import hva.habitat.Habitat;
import hva.tree.Tree;

public class CalculateKeeperSatisfaction extends KeeperCalculator {
    @Override
    public float calculate(Keeper keeper) {
        float work = 0;
        Map<String, Habitat> habitatsList = keeper.getHabitatsList();

        for (Habitat habitat : habitatsList.values()) {
            work += workInHabitat(habitat) / habitat.getNumberOfKeepers();
        }
        return 300 - work;
    }

    private float workInHabitat(Habitat habitat) {
        Map<String, Tree> allTrees = habitat.getTreesList();
        float cleaningDifficulty = 0;
        for (Tree tree : allTrees.values()) {
            cleaningDifficulty += tree.getCleaningDifficulty();
        }
        return habitat.getArea() + 3*habitat.getPopulation() + cleaningDifficulty;
    }
}
