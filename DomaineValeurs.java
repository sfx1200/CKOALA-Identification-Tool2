import java.util.HashSet;
import java.util.Set;

/**
 * Représente le domaine de valeurs autorisé pour une caractéristique,
 * soit un intervalle (numérique), soit un ensemble (symbolique).
 */
public class DomaineValeurs {
    private Double min;
    private Double max;
    private Set<String> elements;
    
    // Constructeur pour un domaine de type INTERVALLE
    public DomaineValeurs(Double min, Double max) {
        this.min = min;
        this.max = max;
        this.elements = null; 
    }

    // Constructeur pour un domaine de type ENSEMBLE
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
        return (this.elements != null) ? new HashSet<>(this.elements) : null;
    }


    public boolean isIntervalle() {
        return this.min != null;
    }

    public boolean isEnsemble() {
        return this.elements != null;
    }

    // Vérifie si une valeur numérique d'une observation est dans le domaine.
    public boolean contient(Double valeur) {
        // Vérification de type 
        if (!this.isIntervalle()) {
            throw new UnsupportedOperationException(
                "Impossible de vérifier une valeur Double sur un domaine de type Ensemble."
            );
        }
        if (valeur == null) {
            return false; 
        }
        return valeur >= this.min && valeur <= this.max;
    }

//  Vérifie si une valeur symbolique (d'une observation) est dans le domaine.
    public boolean contient(String valeur) {
        if (!this.isEnsemble()) {
            throw new UnsupportedOperationException(
                "Impossible de vérifier une valeur String sur un domaine de type Intervalle."
            );
        }
        
        if (valeur == null) {
            return false; 
        }
        return this.elements.contains(valeur);
    }

// Vérifie la contrainte d'inclusion (pour l'héritage des catégories)
    public boolean estCompatible(DomaineValeurs domaineMere) {
        // Deux intervalles
        if (this.isIntervalle() && domaineMere.isIntervalle()) {
            return this.min >= domaineMere.getMin() && this.max <= domaineMere.getMax();
        }

        // Deux ensembles
        if (this.isEnsemble() && domaineMere.isEnsemble()) {
            return domaineMere.getElements().containsAll(this.elements);
        }

        //incompatibles
        return false;
    }
}