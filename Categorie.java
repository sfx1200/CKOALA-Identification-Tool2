import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Categorie { 
    private String nom;
    private Categorie mere;
    private List<Categorie> fils;
    
    private Map<String, DomaineValeurs> caracteristiques;

    public Categorie(String nom) {
        this.nom = nom;
        this.mere = null; 
        this.fils = new ArrayList<>();
        this.caracteristiques = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public Categorie getMere() {
        return mere;
    }

    public List<Categorie> getFils() {
        return fils;
    }

    public Map<String, DomaineValeurs> getCaracteristiques() {
        return caracteristiques;
    }

}