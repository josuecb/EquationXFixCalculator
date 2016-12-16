/**
 * Created by Josue on 12/12/2016.
 */
public class MSymbols {
    private String label;
    private double value;

    public MSymbols(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public boolean equals(MSymbols symbol) {
        return (this.label.equals(symbol.getLabel()));
    }

    public boolean isLabelInList(String label) {
        return this.label.equals(label);
    }

    public double getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
