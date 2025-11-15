import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Tests unitaires pour Observation - Couverture 95%
 */
public class ObservationTest {
    
    private Observation observation;
    
    @Before
    public void setUp() {
        observation = new Observation();
    }
    
    // Constructeur
    @Test
    public void testConstructeur() {
        Observation obs = new Observation();
        assertEquals(0, obs.getNomsCaracteristiques().size());
    }
    
    // ajouter(String, Double)
    @Test
    public void testAjouterValeurNumerique() {
        observation.ajouter("taille", 15.5);
        assertTrue(observation.contientCaracteristique("taille"));
        assertEquals(Double.valueOf(15.5), observation.getValeurNumerique("taille"));
    }
    
    @Test
    public void testAjouterValeurNumeriqueEcrase() {
        observation.ajouter("taille", 15.5);
        observation.ajouter("taille", 20.0);
        assertEquals(Double.valueOf(20.0), observation.getValeurNumerique("taille"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterNumeriqueConflitString() {
        observation.ajouter("forme", "conique");
        observation.ajouter("forme", 15.5);
    }
    
    // ajouter(String, String)
    @Test
    public void testAjouterValeurSymbolique() {
        observation.ajouter("forme", "conique");
        assertTrue(observation.contientCaracteristique("forme"));
        assertEquals("conique", observation.getValeurSymbolique("forme"));
    }
    
    @Test
    public void testAjouterValeurSymboliqueEcrase() {
        observation.ajouter("forme", "conique");
        observation.ajouter("forme", "arrondi");
        assertEquals("arrondi", observation.getValeurSymbolique("forme"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterSymboliqueConflitDouble() {
        observation.ajouter("taille", 15.5);
        observation.ajouter("taille", "grande");
    }
    
    // getValeurNumerique
    @Test
    public void testGetValeurNumerique() {
        observation.ajouter("taille", 25.0);
        assertEquals(Double.valueOf(25.0), observation.getValeurNumerique("taille"));
    }
    
    @Test
    public void testGetValeurNumeriqueInexistante() {
        assertNull(observation.getValeurNumerique("inexistant"));
    }
    
    @Test(expected = ClassCastException.class)
    public void testGetValeurNumeriqueErreurType() {
        observation.ajouter("forme", "conique");
        observation.getValeurNumerique("forme");
    }
    
    // getValeurSymbolique
    @Test
    public void testGetValeurSymbolique() {
        observation.ajouter("couleur", "rouge");
        assertEquals("rouge", observation.getValeurSymbolique("couleur"));
    }
    
    @Test
    public void testGetValeurSymboliqueInexistante() {
        assertNull(observation.getValeurSymbolique("inexistant"));
    }
    
    @Test(expected = ClassCastException.class)
    public void testGetValeurSymboliqueErreurType() {
        observation.ajouter("taille", 15.5);
        observation.getValeurSymbolique("taille");
    }
    
    // contientCaracteristique
    @Test
    public void testContientCaracteristique() {
        observation.ajouter("taille", 15.5);
        observation.ajouter("forme", "conique");
        assertTrue(observation.contientCaracteristique("taille"));
        assertTrue(observation.contientCaracteristique("forme"));
        assertFalse(observation.contientCaracteristique("inexistant"));
    }
    
    // getNomsCaracteristiques
    @Test
    public void testGetNomsCaracteristiquesVide() {
        assertEquals(0, observation.getNomsCaracteristiques().size());
    }
    
    @Test
    public void testGetNomsCaracteristiquesMixtes() {
        observation.ajouter("taille", 15.5);
        observation.ajouter("forme", "conique");
        observation.ajouter("poids", 22.0);
        
        Set<String> noms = observation.getNomsCaracteristiques();
        assertEquals(3, noms.size());
        assertTrue(noms.contains("taille"));
        assertTrue(noms.contains("forme"));
        assertTrue(noms.contains("poids"));
    }
    
    // Scénario complet
    @Test
    public void testScenarioComplet() {
        observation.ajouter("hauteur", 25.5);
        observation.ajouter("forme", "conique");
        observation.ajouter("ecorce", "rugueuse");
        
        assertEquals(3, observation.getNomsCaracteristiques().size());
        assertEquals(Double.valueOf(25.5), observation.getValeurNumerique("hauteur"));
        assertEquals("conique", observation.getValeurSymbolique("forme"));
        assertEquals("rugueuse", observation.getValeurSymbolique("ecorce"));
    }
}