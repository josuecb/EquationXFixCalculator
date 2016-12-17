/**
 * Created by Josue on 12/12/2016.
 */
public class ExpVariable {
    private String label;
    private double value;

    /**
     * This class is meant to be used to store a label and a value
     * (e.i: x = 1)
     * @param label: name of the variable
     * @param value: value of the variable
     */
    public ExpVariable(String label, double value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Checks if variable is equal to another one
     * @param variable
     * @return true if it equal
     */
    public boolean equals(ExpVariable variable) {
        return (this.label.equals(variable.getLabel()));
    }

    /**
     * Checks if variable has this name
     * @param label: name of the variable to check
     * @return true if it is the same name
     */
    public boolean isLabelInList(String label) {
        return this.label.equals(label);
    }

    /**
     * Gets the value of the variable
     * @return variable value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Gets the name of the variable
     * @return variable name
     */
    public String getLabel() {
        return this.label;
    }
}
