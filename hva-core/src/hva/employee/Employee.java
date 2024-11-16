package hva.employee;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    /** The employee's id. */
    private String _id;

    /** The employee's name. */
    private String _name;

    /**
     * @param id
     * @param name
     */
    public Employee(String id, String name) {
        _id = id;
        _name = name;
    }

    /**
     * @return the employee's id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return the employee's name.
     */
    public String getName() {
        return _name;
    }

    public abstract float getSatisfaction();
}
