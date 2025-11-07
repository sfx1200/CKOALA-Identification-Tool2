class CategorieTest {

    /**
     * Teste le constructeur de Categorie et les getters associés.
     * * Il vérifie que lorsqu'on crée une nouvelle Categorie :
     * 1. Le nom est correctement assigné.
     * 2. La mère est initialisée à null.
     * 3. La liste des fils est initialisée (non-null) et vide.
     * 4. La map des caractéristiques est initialisée (non-null) et vide.
     */
    @Test
    void testConstructeurCategorie() {
        // Arrange (Préparation)
        String nomCategorie = "arbre";
        
        // Act (Action)
        Categorie categorie = new Categorie(nomCategorie);

        // Assert (Vérification)
        
        // 1. Vérifie le nom
        assertNotNull(categorie, "La catégorie ne devrait pas être null");
        assertEquals(nomCategorie, categorie.getNom(), "Le nom de la catégorie est incorrect.");

        // 2. Vérifie la mère (doit être null à l'initialisation)
        assertNull(categorie.getMere(), "La catégorie mère devrait être null à l'initialisation.");

        // 3. Vérifie la liste des fils
        List<Categorie> fils = categorie.getFils();
        assertNotNull(fils, "La liste des fils ne devrait pas être null.");
        assertTrue(fils.isEmpty(), "La liste des fils devrait être vide à l'initialisation.");

        // 4. Vérifie la map des caractéristiques
        Map<String, DomaineValeurs> caracs = categorie.getCaracteristiques();
        assertNotNull(caracs, "La map des caractéristiques ne devrait pas être null.");
        assertTrue(caracs.isEmpty(), "La map des caractéristiques devrait être vide à l'initialisation.");
    }
}