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
        this.elements = new HashSet<>(elements); // Copie pour éviter les effets de bord [cite: 53]
    }

    // --- Getters ---
    
    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Set<String> getElements() {
        // Retourne une copie pour respecter l'encapsulation [cite: 91]
        return (this.elements != null) ? new HashSet<>(this.elements) : null;
    }

    // --- Méthodes utilitaires ---

    // Vérifie si le domaine est un intervalle (basé sur l'initialisation de 'min')
    public boolean isIntervalle() {
        return this.min != null;
    }

    // Vérifie si le domaine est un ensemble (basé sur l'initialisation de 'elements')
    public boolean isEnsemble() {
        return this.elements != null;
    }

    // --- Méthodes principales ---

    /**
     * Vérifie si une valeur numérique (d'une observation) est dans le domaine.
     */
    public boolean contient(Double valeur) {
        if (this.isIntervalle() && valeur != null) {
            // Vérifie si la valeur est entre les bornes (incluses)
            return valeur >= this.min && valeur <= this.max;
        }
        return false; // Type incompatible ou valeur null
    }

    /**
     * Vérifie si une valeur symbolique (d'une observation) est dans le domaine.
     */
    public boolean contient(String valeur) {
        if (this.isEnsemble() && valeur != null) {
            // Utilise la vérification rapide du Set
            return this.elements.contains(valeur);
        }
        return false; // Type incompatible ou valeur null
    }

    /**
     * Vérifie la contrainte d'inclusion [cite: 287] (pour l'héritage des catégories).
     * Ce domaine (fille) doit être inclus dans le domaine (mère).
     */
    public boolean estCompatible(DomaineValeurs domaineMere) {
        // Cas 1 : Deux intervalles
        if (this.isIntervalle() && domaineMere.isIntervalle()) {
            // L'intervalle fille doit être inclus dans celui de la mère
            return this.min >= domaineMere.getMin() && this.max <= domaineMere.getMax();
        }

        // Cas 2 : Deux ensembles
        if (this.isEnsemble() && domaineMere.isEnsemble()) {
            // L'ensemble mère doit contenir tous les éléments de la fille
            return domaineMere.getElements().containsAll(this.elements);
        }

        // Cas 3 : Types différents (incompatibles)
        return false;
    }
}