package hva.calculator;

import hva.animal.Animal;
import hva.habitat.Habitat;
import hva.species.Species;

public class CalculateAnimalSatisfaction extends AnimalCalculator {
    @Override
    public float calculate(Animal animal) {
        Habitat habitat = animal.getHabitat();
        int[] numberOfSpecies = getNumberOfSpecies(animal, habitat);

        float satisfaction = 20 + 3*(numberOfSpecies[0]-1) - 2*numberOfSpecies[1] + habitat.getArea()/habitat.getPopulation() + habitat.getAdequacy(animal.getSpecies());

        return satisfaction;
    }

    private int[] getNumberOfSpecies(Animal animal, Habitat habitat) {
        int sameSpecies = 0;
        int differentSpecies = 0;
        Species species = animal.getSpecies();
        for (Animal a : habitat.getAnimalsList().values()) {
            if (species.getId().equals(a.getSpecies().getId())) 
                sameSpecies++;
            else differentSpecies++;
        }
        int[] numberOfSpecies = {sameSpecies, differentSpecies};
        return numberOfSpecies;
    }
}