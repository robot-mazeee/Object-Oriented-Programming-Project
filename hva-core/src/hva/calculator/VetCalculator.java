package hva.calculator;

import hva.employee.Vet;
import java.io.Serializable;

public abstract class VetCalculator implements Serializable {
    public abstract float calculate(Vet vet);
}
