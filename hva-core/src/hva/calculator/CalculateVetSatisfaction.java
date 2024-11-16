package hva.calculator;

import java.util.Map;

import hva.employee.Vet;
import hva.species.Species;

public class CalculateVetSatisfaction extends VetCalculator {
    @Override
    public float calculate(Vet vet) {
        Map<String, Species> speciesList = vet.getAllSpecies();
        int work = 0;

        for (Species species : speciesList.values()) {
            work += species.getPopulation() / species.getNumberOfVets();
        }
        return 20-work;
    }
}
