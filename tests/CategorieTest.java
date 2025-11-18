import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

public class CategorieTest {
    
    private Categorie arbre;
    private Categorie conifere;
    private Categorie epicea;
    
    @BeforeEach
    public void setUp() {
        arbre = new Categorie("arbre");
        conifere = new Categorie("conifère");
        epicea = new Categorie("épicéa");
    }
    
    // CONSTRUCTEUR
    @Test
    public void testConstructeur() {
        assertEquals("arbre", arbre.getNom());
        assertNull(arbre.getMere());
        assertTrue(arbre.getFils().isEmpty());
        assertTrue(arbre.getCaracteristiques().isEmpty());
    }
    
    // SET MERE
    @Test
    public void testSetMere() {
        conifere.setMere(arbre);
        assertEquals(arbre, conifere.getMere());
        assertTrue(arbre.getFils().contains(conifere));
    }
    
    @Test
    public void testSetMereNull() {
        assertThrows(IllegalArgumentException.class, () -> conifere.setMere(null));
    }
    
    @Test
    public void testSetMereAvecCaracteristiquesIncompatibles() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.ajouterCaracteristique("taille", new DomaineValeurs(0.0, 100.0));
        
        assertThrows(IllegalArgumentException.class, () -> conifere.setMere(arbre));
    }
    
    // AJOUTER CARACTERISTIQUE
    @Test
    public void testAjouterCaracteristique() {
        DomaineValeurs domaine = new DomaineValeurs(1.0, 50.0);
        arbre.ajouterCaracteristique("taille", domaine);
        
        assertTrue(arbre.getCaracteristiques().containsKey("taille"));
    }
    
    @Test
    public void testAjouterCaracteristiqueCompatibleAvecMere() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        
        assertDoesNotThrow(() -> {
            conifere.ajouterCaracteristique("taille", new DomaineValeurs(5.0, 30.0));
        });
    }
    
    @Test
    public void testAjouterCaracteristiqueIncompatibleAvecMere() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        
        assertThrows(IllegalArgumentException.class, () -> {
            conifere.ajouterCaracteristique("taille", new DomaineValeurs(0.0, 60.0));
        });
    }
    
    // CONTIENT CARACTERISTIQUE
    @Test
    public void testContientCaracteristiqueDirecte() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        assertTrue(arbre.contientCaracteristique("taille"));
    }
    
    @Test
    public void testContientCaracteristiqueHeritee() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        
        assertTrue(conifere.contientCaracteristique("taille"));
    }
    
    @Test
    public void testNeContientPasCaracteristique() {
        assertFalse(arbre.contientCaracteristique("couleur"));
    }
    
    // GET DOMAINE CARACTERISTIQUE
    @Test
    public void testGetDomaineCaracteristiqueDirecte() {
        DomaineValeurs domaine = new DomaineValeurs(1.0, 50.0);
        arbre.ajouterCaracteristique("taille", domaine);
        
        assertEquals(domaine, arbre.getDomaineCaracteristique("taille"));
    }
    
    @Test
    public void testGetDomaineCaracteristiqueHeritee() {
        DomaineValeurs domaine = new DomaineValeurs(1.0, 50.0);
        arbre.ajouterCaracteristique("taille", domaine);
        conifere.setMere(arbre);
        
        assertEquals(domaine, conifere.getDomaineCaracteristique("taille"));
    }
    
    @Test
    public void testGetDomaineCaracteristiqueInexistante() {
        assertNull(arbre.getDomaineCaracteristique("couleur"));
    }
    
    // GET TOUTES LES CARACTERISTIQUES
    @Test
    public void testGetToutesLesCaracteristiquesDirectes() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        
        Map<String, DomaineValeurs> toutes = arbre.getToutesLesCaracteristiques();
        assertEquals(1, toutes.size());
        assertTrue(toutes.containsKey("taille"));
    }
    
    @Test
    public void testGetToutesLesCaracteristiquesAvecHeritage() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        conifere.ajouterCaracteristique("forme", new DomaineValeurs(Set.of("conique")));
        
        Map<String, DomaineValeurs> toutes = conifere.getToutesLesCaracteristiques();
        assertEquals(2, toutes.size());
        assertTrue(toutes.containsKey("taille"));
        assertTrue(toutes.containsKey("forme"));
    }
    
    @Test
    public void testGetToutesLesCaracteristiquesEcrasement() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        
        DomaineValeurs domaineAffine = new DomaineValeurs(5.0, 30.0);
        conifere.ajouterCaracteristique("taille", domaineAffine);
        
        Map<String, DomaineValeurs> toutes = conifere.getToutesLesCaracteristiques();
        assertEquals(domaineAffine, toutes.get("taille"));
    }
    
    // CORRESPOND A
    @Test
    public void testCorrespondAAvecToutesCaracteristiques() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        
        Observation obs = new Observation();
        obs.ajouter("taille", 25.0);
        
        assertTrue(arbre.correspondA(obs));
    }
    
    @Test
    public void testCorrespondAPasCaracteristiqueManquante() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        
        Observation obs = new Observation();
        // taille manquante
        
        assertFalse(arbre.correspondA(obs));
    }
    
    @Test
    public void testCorrespondAPasValeurHorsDomaine() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        
        Observation obs = new Observation();
        obs.ajouter("taille", 100.0);
        
        assertFalse(arbre.correspondA(obs));
    }
    
    @Test
    public void testCorrespondAAvecHeritage() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        conifere.setMere(arbre);
        conifere.ajouterCaracteristique("forme", new DomaineValeurs(Set.of("conique")));
        
        Observation obs = new Observation();
        obs.ajouter("taille", 25.0);
        obs.ajouter("forme", "conique");
        
        assertTrue(conifere.correspondA(obs));
    }
    
    @Test
    public void testCorrespondAAvecCaracteristiquesSupplementaires() {
        arbre.ajouterCaracteristique("taille", new DomaineValeurs(1.0, 50.0));
        
        Observation obs = new Observation();
        obs.ajouter("taille", 25.0);
        obs.ajouter("couleur", "vert");
        
        assertTrue(arbre.correspondA(obs));
    }
    
    @Test
    public void testCorrespondACategorieVide() {
        Observation obs = new Observation();
        obs.ajouter("taille", 25.0);
        
        assertTrue(arbre.correspondA(obs));
    }
    
    @Test
    public void testCorrespondAAvecEnsemble() {
        arbre.ajouterCaracteristique("forme", new DomaineValeurs(Set.of("conique", "arrondi")));
        
        Observation obs1 = new Observation();
        obs1.ajouter("forme", "conique");
        assertTrue(arbre.correspondA(obs1));
        
        Observation obs2 = new Observation();
        obs2.ajouter("forme", "carré");
        assertFalse(arbre.correspondA(obs2));
    }
}