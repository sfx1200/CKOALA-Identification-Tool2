import java.util.HashSet;
import java.util.Set;

/**
 * Représente le domaine de valeurs autorisé pour une caractéristique.
 */
public class DomaineValeurs {
    private Double min;
    private Double max;

    private Set<String> elements;
    
    public DomaineValeurs(Double min, Double max) {
        this.min = min;
        this.max = max;
        this.elements = null; 
    }

    public DomaineValeurs(Set<String> elements) {
        this.min = null;
        this.max = null;
        this.elements = new HashSet<>(elements);
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Set<String> getElements() {
        return elements;
    }

}