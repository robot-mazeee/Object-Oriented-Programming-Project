package hva.calculator;

import hva.animal.Animal;
import java.io.Serializable;

public abstract class AnimalCalculator implements Serializable {
    public abstract float calculate(Animal animal);
}
