import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categorie { 
    private String nom;
    private Categorie mere;
    private List<Categorie> fils;
    private Map<String, DomaineValeurs> caracteristiques;

    // Constructeur : initialise une catégorie avec un nom
    public Categorie(String nom) {
        this.nom = nom;
        this.mere = null; 
        this.fils = new ArrayList<>();
        this.caracteristiques = new HashMap<>();
    }

    // Retourne le nom de la catégorie
    public String getNom() {
        return nom;
    }

    // Retourne la catégorie mère (ou null si racine)
    public Categorie getMere() {
        return mere;
    }

    // Retourne la liste des catégories filles
    public List<Categorie> getFils() {
        return new ArrayList<>(fils);
    }

    // Retourne les caractéristiques directes de cette catégorie (sans héritage)
    public Map<String, DomaineValeurs> getCaracteristiques() {
        return new HashMap<>(caracteristiques);
    }

    // Définit la catégorie mère et vérifie la compatibilité des caractéristiques existantes
    public void setMere(Categorie mere) {
        if (mere == null) {
            throw new IllegalArgumentException("La catégorie mère ne peut pas être null.");
        }
        for (Map.Entry<String, DomaineValeurs> entry : this.caracteristiques.entrySet()) {
            String nomCaract = entry.getKey();
            DomaineValeurs domaineFille = entry.getValue();
            
            if (mere.contientCaracteristique(nomCaract)) {
                DomaineValeurs domaineMere = mere.getDomaineCaracteristique(nomCaract);
                if (!domaineFille.estCompatible(domaineMere)) {
                    throw new IllegalArgumentException(
                        "La caractéristique '" + nomCaract + 
                        "' n'est pas compatible avec la catégorie mère '" + mere.getNom() + "'."
                    );
                }
            }
        }
        
        this.mere = mere;
        mere.ajouterFils(this);
    }

    // Ajoute une catégorie fille à cette catégorie (méthode privée)
    private void ajouterFils(Categorie fils) {
        if (!this.fils.contains(fils)) {
            this.fils.add(fils);
        }
    }

    // Ajoute une caractéristique en vérifiant la compatibilité avec la mère
    public void ajouterCaracteristique(String nomCaracteristique, DomaineValeurs domaine) {
        if (this.mere != null && this.mere.contientCaracteristique(nomCaracteristique)) {
            DomaineValeurs domaineMere = this.mere.getDomaineCaracteristique(nomCaracteristique);
            if (!domaine.estCompatible(domaineMere)) {
                throw new IllegalArgumentException(
                    "Le domaine de '" + nomCaracteristique + 
                    "' n'est pas compatible avec la catégorie mère '" + this.mere.getNom() + "'."
                );
            }
        }
        
        this.caracteristiques.put(nomCaracteristique, domaine);
    }

    // Vérifie si cette caractéristique existe (directement ou héritée)
    public boolean contientCaracteristique(String nomCaracteristique) {
        if (this.caracteristiques.containsKey(nomCaracteristique)) {
            return true;
        }
        
        if (this.mere != null) {
            return this.mere.contientCaracteristique(nomCaracteristique);
        }
        
        return false;
    }

    // Récupère le domaine d'une caractéristique (directe ou héritée)
    public DomaineValeurs getDomaineCaracteristique(String nomCaracteristique) {
        if (this.caracteristiques.containsKey(nomCaracteristique)) {
            return this.caracteristiques.get(nomCaracteristique);
        }
        
        if (this.mere != null) {
            return this.mere.getDomaineCaracteristique(nomCaracteristique);
        }
        
        return null;
    }

    // Retourne toutes les caractéristiques (directes + héritées de la hiérarchie)
    public Map<String, DomaineValeurs> getToutesLesCaracteristiques() {
        Map<String, DomaineValeurs> toutes = new HashMap<>();
        if (this.mere != null) {
            toutes.putAll(this.mere.getToutesLesCaracteristiques());
        }
        toutes.putAll(this.caracteristiques);
        return toutes;
    }

    // Vérifie si une observation correspond à cette catégorie (toutes les caractéristiques doivent matcher)
    public boolean correspondA(Observation observation) {
        Map<String, DomaineValeurs> toutesCaract = this.getToutesLesCaracteristiques();
        
        for (Map.Entry<String, DomaineValeurs> entry : toutesCaract.entrySet()) {
            String nomCaract = entry.getKey();
            DomaineValeurs domaine = entry.getValue();
            
            if (!observation.contientCaracteristique(nomCaract)) {
                return false;
            }
            
            if (domaine.isIntervalle()) {
                Double valeur = observation.getValeurNumerique(nomCaract);
                if (valeur == null || !domaine.contient(valeur)) {
                    return false;
                }
            } else if (domaine.isEnsemble()) {
                String valeur = observation.getValeurSymbolique(nomCaract);
                if (valeur == null || !domaine.contient(valeur)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}