# Guide de contribution - Projet CKOALA

Ce document décrit la méthode de travail, les choix techniques et l'organisation adoptée pour le développement du noyau fonctionnel du projet CKOALA (Phase 1).
Il est destiné aux membres de l'équipe ainsi qu'aux futurs développeurs (Phase 2) qui reprendront ce code.

## 1. Organisation et Répartition des rôles

### Répartition des tâches (Phase 1)
* **Membre 1 (Soufiane Derouich)** :
  * Conception des classes (`Typologie`, `Categorie`).
  * Implémentation de la gestion des domaines de valeurs et les Catégories (`DomaineValeurs`).
  * Configuration de l'intégration continue (CI/CD GitLab).
  * Rédaction de la documentation (`README.md`, `CONTRIBUTING.md`).

* **Membre 2 (Yassine Bourhaba)** :
  * Développement de la classe `Observation` et gestion des types (Double/String).
  * Écriture des tests unitaires pour les classes (`ObservationTest`, `DomaineValeursTest`,`CategorieTest`).
  * Revue de code et commentaire.
  * configuration de l'intégration continue (CI/CD GitLab)


## 2. Choix Techniques
Nous avons pris plusieurs décisions structurantes pour garantir la robustesse du noyau :

### Gestion des types dans `Observation`
Plutôt que d'utiliser un type générique `Object` (dangereux pour le typage), nous avons choisi d'utiliser deux `Map` distinctes :
* `Map<String, Double>` pour les valeurs numériques.
* `Map<String, String>` pour les valeurs symboliques.
Cela permet de lever des exceptions précises (`ClassCastException`) en cas d'erreur de type à l'exécution.

### Compilation et Tests
Le projet privilégie une approche minimaliste sans gestionnaire de dépendances complexe. L'environnement de développement repose sur :
-Le compilateur standard javac (JDK 17) pour la construction du projet.
-La librairie JUnit 5 (‘via `junit-platform-console-standalone.jar`) pour l'exécution des tests unitaires.


