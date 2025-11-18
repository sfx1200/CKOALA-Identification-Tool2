import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class DomaineValeursTest {

    @Test
    void testConstructeurIntervalle_Valide() {
        DomaineValeurs d = new DomaineValeurs(5.0, 10.0);
        assertTrue(d.isIntervalle());
        assertFalse(d.isEnsemble());
        assertEquals(5.0, d.getMin());
        assertEquals(10.0, d.getMax());
    }

    @Test
    void testConstructeurIntervalle_Invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DomaineValeurs(10.0, 5.0));
        assertThrows(IllegalArgumentException.class, () -> new DomaineValeurs(null, 5.0));
    }

    @Test
    void testConstructeurEnsemble_Valide() {
        Set<String> s = new HashSet<>(Arrays.asList("A", "B"));
        DomaineValeurs d = new DomaineValeurs(s);
        assertTrue(d.isEnsemble());
        assertFalse(d.isIntervalle());
    }

    @Test
    void testConstructeurEnsemble_Invalide() {
        assertThrows(IllegalArgumentException.class, () -> new DomaineValeurs((Set<String>) null));
        assertThrows(IllegalArgumentException.class, () -> new DomaineValeurs(new HashSet<>()));
    }

    @Test
    void testContient_Intervalle() {
        DomaineValeurs d = new DomaineValeurs(0.0, 10.0);
        assertTrue(d.contient(5.0));
        assertFalse(d.contient(11.0));
        assertFalse(d.contient((Double)null));
        
        assertThrows(UnsupportedOperationException.class, () -> d.contient("texte"));
    }

    @Test
    void testContient_Ensemble() {
        DomaineValeurs d = new DomaineValeurs(new HashSet<>(Arrays.asList("A", "B")));
        assertTrue(d.contient("A"));
        assertFalse(d.contient("C"));
        
        assertThrows(UnsupportedOperationException.class, () -> d.contient(5.0));
    }

    @Test
    void testEstCompatible_Intervalle() {
        DomaineValeurs mere = new DomaineValeurs(0.0, 100.0);
        DomaineValeurs filsValide = new DomaineValeurs(10.0, 20.0);
        DomaineValeurs filsInvalide = new DomaineValeurs(-5.0, 10.0);

        assertTrue(filsValide.estCompatible(mere));
        assertFalse(filsInvalide.estCompatible(mere));
    }

    @Test
    void testEstCompatible_Ensemble() {
        DomaineValeurs mere = new DomaineValeurs(new HashSet<>(Arrays.asList("A", "B", "C")));
        DomaineValeurs filsValide = new DomaineValeurs(new HashSet<>(Arrays.asList("A", "B")));
        
        assertTrue(filsValide.estCompatible(mere));
    }
    
    @Test
    void testEstCompatible_Mixte_Invalide() {
        DomaineValeurs intv = new DomaineValeurs(0.0, 10.0);
        DomaineValeurs ens = new DomaineValeurs(new HashSet<>(Arrays.asList("A")));
        
        assertFalse(intv.estCompatible(ens));
        
        assertThrows(IllegalArgumentException.class, () -> intv.estCompatible(null));
    }
}