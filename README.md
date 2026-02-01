# Carnet de Santé Électronique (HEALTHSen)

**Application de bureau native et 100% hors ligne pour la gestion de la santé personnelle et familiale.**

## Table des matières
1.  [Introduction](#1-introduction)
2.  [Fonctionnalités Clés](#2-fonctionnalités-clés)
3.  [Architecture : Frontend vs. Backend](#3-architecture--frontend-vs-backend)
    *   [Le Frontend (JavaFX) : L'Interface Utilisateur](#le-frontend-javafx--linterface-utilisateur)
    *   [Le Backend (Spring Boot) : Le Moteur de l'Application](#le-backend-spring-boot--le-moteur-de-lapplication)
    *   [Communication Frontend-Backend](#communication-frontend-backend--linjection-de-dépendances)
    *   [Exemple Concret : Le Cycle de Connexion](#exemple-concret--le-cycle-de-connexion)
4.  [Technologies Utilisées](#4-technologies-utilisées)
5.  [Démarrage Rapide](#5-démarrage-rapide)
    *   [Pré-requis](#pré-requis)
    *   [Installation](#installation)
    *   [Lancer l'Application](#lancer-lapplication)
    *   [Générer l'Exécutable](#générer-lexécutable)
6.  [Gestion de la Base de Données](#6-gestion-de-la-base-de-données)
7.  [Contribution](#7-contribution)
8.  [Licence](#8-licence)

---

## 1. Introduction

**HEALTHSen** est une solution logicielle innovante conçue pour offrir une gestion privée et sécurisée de vos informations de santé. Développée en **Java** avec **JavaFX** pour l'interface utilisateur et **Spring Boot** pour la logique backend, cette application de bureau fonctionne entièrement **hors ligne**, garantissant la confidentialité et l'accessibilité de vos données médicales à tout moment.

Son objectif est de simplifier le suivi médical pour les individus et les familles en regroupant toutes les données essentielles : rendez-vous, ordonnances, dossiers de santé et plus encore.

## 2. Fonctionnalités Clés

*   **Gestion Complète des Utilisateurs et Profils :** Créez un compte principal et ajoutez des profils pour chaque membre de votre famille.
*   **Tableau de Bord Intuitif :** Visualisez rapidement les informations cruciales, y compris les prochains rendez-vous et les dernières ordonnances.
*   **Gestion Numérique des Ordonnances :** Enregistrez vos ordonnances et joignez des photos ou scans des documents originaux.
*   **Suivi Détaillé des Médicaments :** Maintenez une liste à jour de vos médicaments et enregistrez les prises.
*   **Dossiers de Santé Personnalisés :** Consignez les consultations médicales, les symptômes ressentis et les signes vitaux.
*   **Planification de Rendez-vous :** Organisez et suivez vos rendez-vous médicaux.
*   **Exportation au Format PDF :** Générez des rapports PDF de vos informations de santé pour impression ou partage.

## 3. Architecture : Frontend vs. Backend

Même pour une application de bureau, il est utile de penser en termes de **Frontend** (ce que l'utilisateur voit) et de **Backend** (ce qui se passe en arrière-plan). Cette séparation des préoccupations rend le code plus propre, plus facile à maintenir et à faire évoluer.

### Le Frontend (JavaFX) : L'Interface Utilisateur

Le Frontend, c'est l'ensemble de l'interface graphique avec laquelle l'utilisateur interagit.

*   **Technologie principale :** **JavaFX**.
*   **Rôle :** Affiche les fenêtres, les boutons, les formulaires et toutes les informations visibles à l'écran.
*   **Composants clés :**
    1.  **Vues (`.fxml`) :** Situés dans `src/main/resources/fxml/`, ces fichiers décrivent la structure et la disposition de chaque écran (ex: `login.fxml`, `dashboard.fxml`). C'est le "squelette" de l'interface.
    2.  **Contrôleurs (`Controller`) :** Situés dans `src/main/java/com/senegalsante/controller/javafx/`, ces classes Java font le lien entre les vues et le reste de l'application. Elles gèrent les actions de l'utilisateur (clics, saisie) et mettent à jour l'affichage.

### Le Backend (Spring Boot) : Le Moteur de l'Application

Le Backend est le moteur invisible qui contient toute la logique de l'application, gère les données et les règles métier. Il est entièrement orchestré par **Spring Boot**.

*   **Technologie principale :** **Spring Boot**.
*   **Rôle :** Exécuter les opérations, traiter les données et communiquer avec la base de données.
*   **Composants clés (Architecture en couches) :**
    1.  **Services (`@Service`) :** Le cœur de la logique métier. Ces classes (dans `src/main/java/com/senegalsante/service/`) définissent les opérations complexes (ex: comment inscrire un utilisateur, comment créer une ordonnance).
    2.  **Repositories (`JpaRepository`) :** La couche d'accès aux données. Ces interfaces (dans `src/main/java/com/senegalsante/repository/`) utilisent **Spring Data JPA** pour communiquer avec la base de données de manière simple et efficace, sans avoir à écrire de requêtes SQL manuellement.
    3.  **Modèles (`@Entity`) :** La définition de la structure des données. Ces classes (dans `src/main/java/com/senegalsante/model/`) sont le plan de la base de données **SQLite**. Chaque classe correspond à une table.

### Communication Frontend-Backend : L'Injection de Dépendances

La communication entre le Frontend (Contrôleurs JavaFX) et le Backend (Services) est assurée par le mécanisme d'**injection de dépendances** de Spring.

1.  Un Contrôleur JavaFX est déclaré comme un composant Spring (`@Component`).
2.  Il peut alors demander une instance d'un Service ou d'un Repository du Backend en utilisant l'annotation `@Autowired`.
3.  Spring "injecte" automatiquement l'instance requise dans le contrôleur.
4.  Le contrôleur peut ainsi appeler les méthodes du backend directement, comme si c'était un composant local.

Ce mécanisme permet une **faible-couplage** : le frontend n'a pas besoin de savoir comment le backend est construit, il a juste besoin de savoir quelles méthodes appeler.

### Exemple Concret : Le Cycle de Connexion

1.  **Frontend (Vue `login.fxml`) :** L'utilisateur clique sur "Se connecter".
2.  **Frontend (Contrôleur `LoginController`) :** La méthode `@FXML` `handleLogin()` est déclenchée.
3.  **Pont Frontend-Backend :** Le contrôleur utilise son `UserRepository` (injecté via `@Autowired`) pour appeler la méthode `findByEmail(...)`.
4.  **Backend (Repository `UserRepository`) :** Spring Data JPA traduit l'appel en requête SQL, l'exécute sur la base de données SQLite et retourne un objet `User`.
5.  **Retour au Frontend :** Le `LoginController` reçoit l'objet `User`, vérifie le mot de passe, et si tout est correct, charge la vue `dashboard.fxml` pour l'afficher à l'utilisateur.

## 4. Technologies Utilisées

*   **Langage :** Java 17
*   **Framework d'Application (Backend) :** Spring Boot 3.2.0
*   **Interface Utilisateur (Frontend) :** OpenJFX (JavaFX) 21.0.1
*   **Accès aux Données :** Spring Data JPA & Hibernate
*   **Base de Données :** SQLite
*   **Outil de Build :** Apache Maven

## 5. Démarrage Rapide

### Pré-requis

*   **Java Development Kit (JDK) 17 ou supérieur**
*   **Apache Maven 3.x ou supérieur**

### Installation

1.  **Clonez le dépôt :** `git clone <URL_DU_DEPOT>` et `cd CARNET_SANTE_GITHUB`
2.  **Construire le projet :** `mvn clean install` (télécharge les dépendances).

### Lancer l'Application

```bash
mvn clean javafx:run
```

### Générer l'Exécutable JAR

Pour créer un fichier `.jar` unique et autonome :
```bash
mvn clean package
```
Lancez-le avec : `java -jar target/carnet-sante-1.0.0.jar`.

## 6. Gestion de la Base de Données

L'application utilise **SQLite** pour un stockage 100% local. Le fichier `senegal_sante.db` est créé automatiquement à la racine du projet. Pour les développeurs, le paramètre `spring.jpa.hibernate.ddl-auto=update` dans `application.properties` permet à Hibernate de mettre à jour le schéma de la base de données au démarrage.

## 7. Contribution

Les contributions sont les bienvenues. Veuillez forker le dépôt, créer une branche pour votre fonctionnalité et soumettre une Pull Request.

## 8. Licence

Ce projet est sous licence [MIT License](LICENSE). <!-- À ajuster si nécessaire -->

---
**Note :** N'oubliez pas de remplacer l'URL du dépôt GitHub par la valeur réelle.
#   C A R N E T _ S A N T E _ G I T H U B  
 