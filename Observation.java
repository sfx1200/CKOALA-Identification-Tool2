import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Pour cette classe on traite à part les valeurs à part écrits par l'utilisateur
// soit des double , soit des chaines de caractères.
public class Observation {
    private Map<String, Double> valeursNumeriques;
    private Map<String, String> valeursSymboliques;

    public Observation() {
        this.valeursNumeriques = new HashMap<>(); 
        this.valeursSymboliques = new HashMap<>();
    }
    
   //Ajoute ou met à jour une valeur de type double observée dans la map 
    public void ajouter(String caracteristique, Double valeur) {
        if (this.valeursSymboliques.containsKey(caracteristique)) {
            throw new IllegalArgumentException(
                "Conflit de type : La caractéristique '" + caracteristique + "' existe déjà en tant que String."
            );
        }
        this.valeursNumeriques.put(caracteristique, valeur);
    }
    
   //Ajoute ou met à jour une valeur de type string observée dans la map .
    public void ajouter(String caracteristique, String valeur) {
        if (this.valeursNumeriques.containsKey(caracteristique)) {
            throw new IllegalArgumentException(
                "Conflit de type : La caractéristique '" + caracteristique + "' existe déjà en tant que Double."
            );
        }
        this.valeursSymboliques.put(caracteristique, valeur);
    }

     // Récupère la valeur numérique  associée à une caractéristique.
    public Double getValeurNumerique(String caracteristique) {
        if (this.valeursSymboliques.containsKey(caracteristique)) {
            throw new ClassCastException(
                "Erreur de type : '" + caracteristique + "' est une valeur String, pas Double."
            );
        }
        return this.valeursNumeriques.get(caracteristique);
    }

    //Récupère la valeur symbolique associée à une caractéristique
    public String getValeurSymbolique(String caracteristique) {
        if (this.valeursNumeriques.containsKey(caracteristique)) {
            throw new ClassCastException(
                "Erreur de type : '" + caracteristique + "' est une valeur Double, pas String."
            );
        }
        return this.valeursSymboliques.get(caracteristique);
    }

   // Cherche dans les 2 map si ils contiennent la caracteristique donnée
    public boolean contientCaracteristique(String caracteristique) {
        return this.valeursNumeriques.containsKey(caracteristique) || 
               this.valeursSymboliques.containsKey(caracteristique);
    }

    
    // Retourne l'ensemble de tous les noms de caractéristiques de l'observation.
     
    public Set<String> getNomsCaracteristiques() {
        Set<String> noms = new HashSet<>(this.valeursNumeriques.keySet());
        noms.addAll(this.valeursSymboliques.keySet());
        return noms;
    }
}