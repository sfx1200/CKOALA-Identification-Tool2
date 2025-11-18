import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

public class LireXML {
    public static void main(String[] args) {
        try {
            // Charger le fichier XML
            File xmlFile = new File(args[0]);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parser et normaliser
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Racine du document : " + doc.getDocumentElement().getNodeName());
            System.out.println("---------------------------------------");

            // Récupérer toutes les balises <categorie>
            NodeList categories = doc.getElementsByTagName("categorie");

            for (int i = 0; i < categories.getLength(); i++) {
                Element cat = (Element) categories.item(i);

                String nom = cat.getElementsByTagName("nom").item(0).getTextContent();
                String mere = cat.getElementsByTagName("mere").item(0).getTextContent();

                System.out.println("Catégorie : " + nom);
                System.out.println("  Mère : " + mere);

                // Récupérer les caractéristiques
                NodeList caracs = cat.getElementsByTagName("caracteristique");

                for (int j = 0; j < caracs.getLength(); j++) {
                    Element carac = (Element) caracs.item(j);
                    String intitule = carac.getElementsByTagName("intitule").item(0).getTextContent();

                    System.out.print("    Caractéristique : " + intitule + " → ");

                    // Si c’est un ensemble
                    if (carac.getElementsByTagName("ensemble").getLength() > 0) {
                        NodeList elements = carac.getElementsByTagName("element");
                        System.out.print("[");
                        for (int k = 0; k < elements.getLength(); k++) {
                            System.out.print(elements.item(k).getTextContent());
                            if (k < elements.getLength() - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println("]");
                    }

                    // Si c’est un intervalle
                    if (carac.getElementsByTagName("intervalle").getLength() > 0) {
                        Element intervalle = (Element) carac.getElementsByTagName("intervalle").item(0);
                        String inf = intervalle.getElementsByTagName("inf").item(0).getTextContent();
                        String sup = intervalle.getElementsByTagName("sup").item(0).getTextContent();
                        System.out.println("[" + inf + " ; " + sup + "]");
                    }
                }

                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}