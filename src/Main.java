import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== DÉMARRAGE DU TEST CKOALA ===");

        // ---------------------------------------------------------
        // ÉTAPE 1 : Initialisation et Chargement XML
        // ---------------------------------------------------------
        System.out.println("\n[1] Création de la typologie et chargement du fichier XML...");
        Typologie foret = new Typologie("Ma Forêt");

        try {
            // Remplacez "arbres.xml" par le chemin exact si nécessaire
            foret.chargerDepuisXML("arbres.xml");
            System.out.println("--> Chargement réussi !");
            System.out.println("--> Nombre de catégories chargées : " + foret.getCategories().size());
            
            // Vérification rapide d'un lien parent-enfant
            Categorie epicea = foret.getCategorie("epicea");
            if (epicea != null && epicea.getMere() != null) {
                System.out.println("--> Vérification structure : 'epicea' a pour mère '" + epicea.getMere().getNom() + "' (Correct).");
            } else {
                System.err.println("--> Erreur : La catégorie 'epicea' ou sa mère est introuvable.");
            }

        } catch (Exception e) {
            System.err.println("ERREUR CRITIQUE lors du chargement XML : " + e.getMessage());
            e.printStackTrace();
            return; // On arrête tout si le chargement échoue
        }

        // ---------------------------------------------------------
        // ÉTAPE 2 : Test de Classification - Cas "Épicéa"
        // ---------------------------------------------------------
        System.out.println("\n[2] Test de classification : Simulation d'un Épicéa");
        System.out.println("    (On rentre des valeurs qui correspondent à : Arbre -> Conifère -> Épicéa)");

        Observation obsEpicea = new Observation();
        
        // Critères généraux (Arbre)
        obsEpicea.ajouter("forme", "conique");
        obsEpicea.ajouter("taille", 25.0);        // Dans [5.0, 50.0] pour arbre, [20.0, 30.0] pour épicéa
        obsEpicea.ajouter("taille du tronc", 1.0); // Dans [0.5, 1.5] pour épicéa
        obsEpicea.ajouter("ecorce", "ecailles");
        
        // Critère spécifique (Épicéa)
        obsEpicea.ajouter("aiguilles", "brosse");

        System.out.println("    Observation : " + obsEpicea.getNomsCaracteristiques());

        // Lancement de la classification
        List<Categorie> resultatsEpicea = foret.classifier(obsEpicea);

        System.out.println("--> RÉSULTATS (Catégories compatibles) :");
        if (resultatsEpicea.isEmpty()) {
            System.out.println("    Aucune correspondance trouvée.");
        } else {
            for (Categorie c : resultatsEpicea) {
                System.out.println("    - " + c.getNom());
            }
        }
        
        // ---------------------------------------------------------
        // ÉTAPE 3 : Test de Classification - Cas "Chêne"
        // ---------------------------------------------------------
        System.out.println("\n[3] Test de classification : Simulation d'un Chêne");
        System.out.println("    (On rentre des valeurs typiques d'un feuillu)");

        Observation obsChene = new Observation();
        obsChene.ajouter("forme", "irregulier");
        obsChene.ajouter("taille", 30.0);
        obsChene.ajouter("taille du tronc", 1.8);
        obsChene.ajouter("ecorce", "fissuree");
        obsChene.ajouter("feuilles", "lobe"); // Spécifique au chêne

        List<Categorie> resultatsChene = foret.classifier(obsChene);

        System.out.println("--> RÉSULTATS :");
        for (Categorie c : resultatsChene) {
            System.out.println("    - " + c.getNom());
        }

        // ---------------------------------------------------------
        // ÉTAPE 4 : Test d'échec (Valeur hors domaine)
        // ---------------------------------------------------------
        System.out.println("\n[4] Test de classification : Cas incompatible (Arbre nain)");
        System.out.println("    (Taille = 0.1m, alors que 'arbre' demande min 5.0m)");

        Observation obsErreur = new Observation();
        obsErreur.ajouter("forme", "conique");
        obsErreur.ajouter("taille", 0.1); // Trop petit !

        List<Categorie> resultatsErreur = foret.classifier(obsErreur);
        
        System.out.println("--> RÉSULTATS :");
        if (resultatsErreur.isEmpty()) {
            System.out.println("    Aucune correspondance (Comportement normal attendu).");
        } else {
            for (Categorie c : resultatsErreur) {
                System.out.println("    - " + c.getNom());
            }
        }

        System.out.println("\n=== FIN DU TEST ===");
    }
}