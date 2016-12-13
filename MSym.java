/**
 * Created by Josue on 12/12/2016.
 */
public class MSym {
    private String label;
    private long value;

    public MSym(String label, long value) {
        this.label = label;
        this.value = value;
    }

    public boolean equals(MSym symbol) {
        return (this.label.equals(symbol.getLabel()));
    }

    public long getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
