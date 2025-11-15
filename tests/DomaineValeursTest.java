import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests unitaires pour DomaineValeurs - Couverture 95%
 */
public class DomaineValeursTest {
    
    private DomaineValeurs intervalle;
    private DomaineValeurs ensemble;
    
    @Before
    public void setUp() {
        intervalle = new DomaineValeurs(5.0, 50.0);
        Set<String> elements = new HashSet<>();
        elements.add("conique");
        elements.add("arrondi");
        ensemble = new DomaineValeurs(elements);
    }
    
    // Constructeurs INTERVALLE
    @Test
    public void testConstructeurIntervalle() {
        DomaineValeurs d = new DomaineValeurs(10.0, 20.0);
        assertEquals(Double.valueOf(10.0), d.getMin());
        assertEquals(Double.valueOf(20.0), d.getMax());
        assertTrue(d.isIntervalle());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIntervalleMinNull() {
        new DomaineValeurs(null, 20.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIntervalleMaxNull() {
        new DomaineValeurs(10.0, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIntervalleMinSupMax() {
        new DomaineValeurs(30.0, 10.0);
    }
    
    // Constructeurs ENSEMBLE
    @Test
    public void testConstructeurEnsemble() {
        assertEquals(2, ensemble.getElements().size());
        assertTrue(ensemble.isEnsemble());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEnsembleNull() {
        new DomaineValeurs((Set<String>) null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEnsembleVide() {
        new DomaineValeurs(new HashSet<String>());
    }
    
    // Getters
    @Test
    public void testGetters() {
        assertEquals(Double.valueOf(5.0), intervalle.getMin());
        assertEquals(Double.valueOf(50.0), intervalle.getMax());
        assertNull(ensemble.getMin());
        assertNull(ensemble.getMax());
    }
    
    // contient(Double)
    @Test
    public void testContientDouble() {
        assertTrue(intervalle.contient(10.0));
        assertTrue(intervalle.contient(5.0));
        assertFalse(intervalle.contient(4.9));
        assertFalse(intervalle.contient((Double) null));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testContientDoubleSurEnsemble() {
        ensemble.contient(10.0);
    }
    
    // contient(String)
    @Test
    public void testContientString() {
        assertTrue(ensemble.contient("conique"));
        assertFalse(ensemble.contient("carre"));
        assertFalse(ensemble.contient((String) null));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testContientStringSurIntervalle() {
        intervalle.contient("test");
    }
    
    // estCompatible
    @Test
    public void testCompatibleIntervalles() {
        DomaineValeurs mere = new DomaineValeurs(0.0, 100.0);
        DomaineValeurs fille = new DomaineValeurs(10.0, 50.0);
        assertTrue(fille.estCompatible(mere));
        assertFalse(mere.estCompatible(fille));
    }
    
    @Test
    public void testCompatibleEnsembles() {
        Set<String> m = new HashSet<>();
        m.add("conique");
        m.add("arrondi");
        m.add("plat");
        DomaineValeurs mere = new DomaineValeurs(m);
        
        Set<String> f = new HashSet<>();
        f.add("conique");
        DomaineValeurs fille = new DomaineValeurs(f);
        
        assertTrue(fille.estCompatible(mere));
    }
    
    @Test
    public void testIncompatibleTypes() {
        assertFalse(intervalle.estCompatible(ensemble));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCompatibleNull() {
        intervalle.estCompatible(null);
    }
    
    // toString
    @Test
    public void testToString() {
        assertTrue(intervalle.toString().contains("5.0"));
    }
}