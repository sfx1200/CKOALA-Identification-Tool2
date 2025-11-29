
# Pepline Status : 
# TEST DIAGNOSTIC (Gardez celui qui s'affiche)

1. Essai Main (tout minuscule) :
![test1](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/badges/main/pipeline.svg)

2. Essai Master (tout minuscule) :
![test2](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/badges/master/pipeline.svg)

3. Essai Main (Avec Majuscules probables) :
![test3](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/Projet_PPO_CKOALA/badges/main/pipeline.svg)

4. Essai Master (Avec Majuscules probables) :
![test4](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/Projet_PPO_CKOALA/badges/master/pipeline.svg)

---


[![pipeline status](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/badges/master/pipeline.svg)](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/-/commits/master)
[![coverage report](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/badges/master/coverage.svg)](https://gitlab.univ-lille.fr/yassine.bourhaba.etu/projet_ppo_ckoala/-/commits/master)

# Projet PPO - CKOALA : Noyau Fonctionnel

Ce projet implémente le noyau fonctionnel de l'application CKOALA (C'est Quoi Là ?), un assistant numérique d'aide à la classification d'observations naturelles.

Il permet de modéliser une typologie hiérarchique (catégories, caractéristiques, domaines de valeurs) et de classifier dynamiquement une observation saisie par l'utilisateur.

## Description du projet

L'application repose sur les concepts suivants :
* **Typologie** : Structure arborescente regroupant des catégories.
* **Catégorie** : Entité définie par un ensemble de caractéristiques (ex: `Conifère` hérite de `Arbre`).
* **Domaine de Valeurs** : Contraintes associées aux caractéristiques.
  * *Intervalles numériques* : Pour des données continues (ex: taille du tronc `[0.5 ; 3.0]`).
  * *Ensembles de symboles* : Pour des données textuelles (ex: forme `{conique, arrondi, irrégulier}` ou écorce `{lisse, fissurée, écailles}`).
* **Observation** : Données saisies par l'utilisateur à classifier (ex: `forme=conique`, `taille=12.5`).

## Structure du projet

* `src/` : Contient le code source Java du noyau fonctionnel.
* `tests/` : Contient les classes de tests unitaires.
* `lib/` : Contient les dépendances externes (JUnit).

## Compilation

 La compilation s'effectue via `javac` en incluant la librairie de test dans le classpath.

Exécutez les commandes suivantes à la racine du projet :


mkdir -p out
javac -encoding UTF-8 -cp lib/junit-platform-console-standalone.jar -d out/ src/*.java tests/*.java

## Tests Unitaires

Le projet suit une démarche de développement guidé par les tests (TDD). Une suite de tests complète garantit la robustesse du noyau et le respect des règles métier.

### Ce qui est testé
* **Observation** : Vérification du stockage distinct des types (Double vs String) et gestion des conflits d'insertion.
* **DomaineValeurs** : Validation des intervalles (min <= max) et vérification stricte de l'inclusion des ensembles (sous-ensembles).
* **Categorie** : Vérification de l'héritage et de la compatibilité stricte des caractéristiques entre une fille et sa mère.
* **Typologie** : Test du chargement XML et de l'algorithme de classification (`classifier`).

### Comment exécuter les tests

Assurez-vous d'abord que les fichiers de tests sont bien compilés dans le dossier `out/` :
```bash
##Compilation
javac -encoding UTF-8 -cp lib/junit-platform-console-standalone.jar -d out/ src/*.java tests/*.java
##Execution
java -jar lib/junit-platform-console-standalone.jar --class-path out/ --scan-classpath