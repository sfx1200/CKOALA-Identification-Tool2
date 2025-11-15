import java.util.HashSet;
import java.util.Set;

/**
 * Représente le domaine de valeurs autorisé pour une caractéristique.
 * Peut être soit un intervalle numérique [min, max], soit un ensemble symbolique.
 */
public class DomaineValeurs {
    private final Double min;
    private final Double max;
    private final Set<String> elements;
    
    // Constructeur pour un domaine de type INTERVALLE
    public DomaineValeurs(Double min, Double max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("min et max ne peuvent pas être null");
        }
        if (min > max) {
            throw new IllegalArgumentException("min doit être inférieur ou égal à max");
        }
        this.min = min;
        this.max = max;
        this.elements = null; 
    }

    // Constructeur pour un domaine de type ENSEMBLE
    public DomaineValeurs(Set<String> elements) {
        if (elements == null || elements.isEmpty()) {
            throw new IllegalArgumentException("L'ensemble ne peut pas être null ou vide");
        }
        this.min = null;
        this.max = null;
        this.elements = new HashSet<>(elements);
    }
    
    // Retourne la borne minimale (null si ensemble)
    public Double getMin() {
        return min;
    }

    // Retourne la borne maximale (null si ensemble)
    public Double getMax() {
        return max;
    }

    // Retourne une copie de l'ensemble (null si intervalle)
    public Set<String> getElements() {
        return (this.elements != null) ? new HashSet<>(this.elements) : null;
    }

    // Vérifie si ce domaine est un intervalle
    public boolean isIntervalle() {
        return this.min != null;
    }

    // Vérifie si ce domaine est un ensemble
    public boolean isEnsemble() {
        return this.elements != null;
    }

    // Vérifie si une valeur numérique est dans le domaine
    public boolean contient(Double valeur) {
        if (!this.isIntervalle()) {
            throw new UnsupportedOperationException(
                "Impossible de vérifier une valeur Double sur un domaine de type Ensemble"
            );
        }
        if (valeur == null) {
            return false; 
        }
        return valeur >= this.min && valeur <= this.max;
    }

    // Vérifie si une valeur symbolique est dans le domaine
    public boolean contient(String valeur) {
        if (!this.isEnsemble()) {
            throw new UnsupportedOperationException(
                "Impossible de vérifier une valeur String sur un domaine de type Intervalle"
            );
        }
        if (valeur == null) {
            return false; 
        }
        return this.elements.contains(valeur);
    }

    // Vérifie la contrainte d'inclusion (pour l'héritage des catégories)
    public boolean estCompatible(DomaineValeurs domaineMere) {
        if (domaineMere == null) {
            throw new IllegalArgumentException("Le domaine mère ne peut pas être null");
        }
        
        // Deux intervalles
        if (this.isIntervalle() && domaineMere.isIntervalle()) {
            return this.min >= domaineMere.getMin() && this.max <= domaineMere.getMax();
        }

        // Deux ensembles
        if (this.isEnsemble() && domaineMere.isEnsemble()) {
            return domaineMere.getElements().containsAll(this.elements);
        }

        // Types incompatibles
        return false;
    }
    
    // Retourne une représentation textuelle du domaine
    @Override
    public String toString() {
        if (this.isIntervalle()) {
            return "[" + min + ", " + max + "]";
        } else {
            return elements.toString();
        }
    }
}