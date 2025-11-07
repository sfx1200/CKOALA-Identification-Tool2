import java.util.HashMap;
import java.util.Map;

public class Observation {
    //Pour cette classe on traite à part les valeurs à part écrits par l'utilisateur 
    // soit des double , soit des chaines de caractères.
    private Map<String, Double> valeursNumeriques;
    private Map<String, String> valeursSymboliques;

    public Observation() {
        this.valeursNumeriques = new HashMap<>(); 
        this.valeursSymboliques = new HashMap<>();
    }
    //Ajoute ou met à jour une valeur de type double observée dans la map .   
    public void ajouter(String caracteristique, Double valeur) {
        this.valeursNumeriques.put(caracteristique, valeur);
    }
    //Ajoute ou met à jour une valeur de type string observée dans la map .
    public void ajouter(String caracteristique, String valeur) {
        this.valeursSymboliques.put(caracteristique, valeur);
    }

    // Récupère la valeur numérique  associée à une caractéristique.
    public Double getValeurNumerique(String caracteristique) {
        return this.valeursNumeriques.get(caracteristique);
    }

    //Récupère la valeur symbolique associée à une caractéristique.
    public String getValeurSymbolique(String caracteristique) {
        return this.valeursSymboliques.get(caracteristique);
    }

    // Cherche dans les 2 map si ils contiennent la caracteristique donnée.
    public boolean contientCaracteristique(String caracteristique) {
        return this.valeursNumeriques.containsKey(caracteristique) || 
               this.valeursSymboliques.containsKey(caracteristique);
    }
}