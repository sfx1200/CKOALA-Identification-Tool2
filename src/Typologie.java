import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Typologie {
    private String nom;
    private Map<String, Categorie> categories;
   
    private List<Categorie> racines; 

    public Typologie(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la typologie ne peut pas être null.");
        }
        this.nom = nom;
        this.categories = new HashMap<>();
        this.racines = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public Categorie getCategorie(String nomCategorie) {
        return this.categories.get(nomCategorie);
    }

    public List<Categorie> getCategories() {
        return new ArrayList<>(this.categories.values());
    }


    public void ajouterCategorie(Categorie categorie) {
        if (categorie == null) return;
        
        String nomCategorie = categorie.getNom();
        if (!this.categories.containsKey(nomCategorie)) {
            this.categories.put(nomCategorie, categorie);
            
            if (categorie.getMere() == null) {
                this.racines.add(categorie);
            }
        }
    }

    public List<Categorie> classifier(Observation observation) {
        if (observation == null) {
            throw new IllegalArgumentException("L'observation ne peut pas être null.");
        }
        
        List<Categorie> categoriesCompatibles = new ArrayList<>();

        for (Categorie categorie : this.categories.values()) {
            if (categorie.correspondA(observation)) {
                categoriesCompatibles.add(categorie);
            }
        }
        
        return categoriesCompatibles;
    }


    public void chargerDepuisXML(String cheminFichier) {
        try {
            File xmlFile = new File(cheminFichier);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listeCategoriesXML = doc.getElementsByTagName("categorie");

            for (int i = 0; i < listeCategoriesXML.getLength(); i++) {
                Element catElement = (Element) listeCategoriesXML.item(i);
                String nomCat = catElement.getElementsByTagName("nom").item(0).getTextContent();
                
                if (!nomCat.equals("TOP") && !this.categories.containsKey(nomCat)) {
                    this.ajouterCategorie(new Categorie(nomCat));
                }
            }

            for (int i = 0; i < listeCategoriesXML.getLength(); i++) {
                Element catElement = (Element) listeCategoriesXML.item(i);
                String nomCat = catElement.getElementsByTagName("nom").item(0).getTextContent();
                
                if (nomCat.equals("TOP")) continue;

                Categorie categorieCourante = this.categories.get(nomCat);
                
                // Mère
                if (catElement.getElementsByTagName("mere").getLength() > 0) {
                    String nomMere = catElement.getElementsByTagName("mere").item(0).getTextContent();
                    if (!nomMere.equals("TOP")) {
                        Categorie mere = this.categories.get(nomMere);
                        if (mere != null) {
                            // Note: setMere vérifie déjà la cohérence (inclusions)
                            categorieCourante.setMere(mere);
                            // Ce n'est plus une racine
                            this.racines.remove(categorieCourante);
                        }
                    }
                }

                // Caractéristiques
                NodeList caracsXML = catElement.getElementsByTagName("caracteristique");
                for (int j = 0; j < caracsXML.getLength(); j++) {
                    Element caracElement = (Element) caracsXML.item(j);
                    String intitule = caracElement.getElementsByTagName("intitule").item(0).getTextContent();
                    DomaineValeurs domaine = null;

                    if (caracElement.getElementsByTagName("ensemble").getLength() > 0) {
                        Set<String> symboles = new HashSet<>();
                        NodeList elements = caracElement.getElementsByTagName("element");
                        for (int k = 0; k < elements.getLength(); k++) {
                            symboles.add(elements.item(k).getTextContent());
                        }
                        domaine = new DomaineValeurs(symboles);
                    } else if (caracElement.getElementsByTagName("intervalle").getLength() > 0) {
                        Element intervalle = (Element) caracElement.getElementsByTagName("intervalle").item(0);
                        Double inf = Double.parseDouble(intervalle.getElementsByTagName("inf").item(0).getTextContent());
                        Double sup = Double.parseDouble(intervalle.getElementsByTagName("sup").item(0).getTextContent());
                        domaine = new DomaineValeurs(inf, sup);
                    }

                    if (domaine != null) {
                        // Note: ajouterCaracteristique vérifie aussi la cohérence
                        categorieCourante.ajouterCaracteristique(intitule, domaine);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement du XML : " + e.getMessage());
        }
    }
}