package hva.calculator;

import hva.employee.Keeper;
import java.io.Serializable;

public abstract class KeeperCalculator implements Serializable {
    public abstract float calculate(Keeper keeper);
}
