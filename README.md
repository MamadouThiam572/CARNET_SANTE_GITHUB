# 🩺 Carnet de Santé Électronique (HEALTHSen)

**Application de bureau native, sécurisée et 100 % hors ligne pour la gestion de la santé personnelle et familiale.**

---

## 📑 Table des matières

1. [Introduction](#1-introduction)
2. [Fonctionnalités clés](#2-fonctionnalités-clés)
3. [Architecture : Frontend vs Backend](#3-architecture--frontend-vs-backend)

   * [Le Frontend (JavaFX) : l’interface utilisateur](#le-frontend-javafx--linterface-utilisateur)
   * [Le Backend (Spring Boot) : le moteur de l’application](#le-backend-spring-boot--le-moteur-de-lapplication)
   * [Communication Frontend–Backend](#communication-frontend-backend--linjection-de-dépendances)
   * [Exemple concret : cycle de connexion](#exemple-concret--le-cycle-de-connexion)
4. [Technologies utilisées](#4-technologies-utilisées)
5. [Démarrage rapide](#5-démarrage-rapide)

   * [Pré-requis](#pré-requis)
   * [Installation](#installation)
   * [Lancer l’application](#lancer-lapplication)
   * [Générer l’exécutable](#générer-lexécutable)
6. [Gestion de la base de données](#6-gestion-de-la-base-de-données)
7. [Contribution](#7-contribution)
8. [Licence](#8-licence)

---

## 1. Introduction

**HEALTHSen** est une solution logicielle moderne conçue pour offrir une **gestion privée, sécurisée et centralisée** des informations de santé.

Développée en **Java**, avec **JavaFX** pour l’interface graphique et **Spring Boot** pour la logique métier, l’application fonctionne **entièrement hors ligne**, garantissant :

* 🔒 la confidentialité des données médicales,
* ⚡ une disponibilité permanente,
* 🖥️ une utilisation fluide sur poste de travail.

L’objectif principal est de **simplifier le suivi médical individuel et familial** en regroupant au même endroit toutes les informations essentielles : dossiers médicaux, ordonnances, médicaments et rendez-vous.

---

## 2. Fonctionnalités clés

* 👤 **Gestion des utilisateurs et profils**
  Création d’un compte principal avec gestion de plusieurs profils (famille).

* 📊 **Tableau de bord intuitif**
  Vue synthétique des informations importantes : prochains rendez-vous, ordonnances récentes, traitements en cours.

* 💊 **Gestion numérique des ordonnances**
  Enregistrement des ordonnances avec possibilité d’ajouter des images ou des scans.

* 🧾 **Suivi des médicaments**
  Liste détaillée des médicaments avec historique des prises.

* 🩻 **Dossiers de santé personnalisés**
  Historique des consultations, symptômes, diagnostics et signes vitaux.

* 📅 **Planification des rendez-vous médicaux**
  Organisation et suivi des consultations.

* 📄 **Exportation PDF**
  Génération de rapports médicaux imprimables ou partageables.

---

## 3. Architecture : Frontend vs Backend

Même pour une application de bureau, l’architecture repose sur une **séparation claire des responsabilités** :

* **Frontend** : interface utilisateur (IHM)
* **Backend** : logique métier et gestion des données

Cette approche améliore la **maintenabilité**, la **lisibilité du code** et l’**évolutivité** du projet.

---

### Le Frontend (JavaFX) : l’interface utilisateur

Le Frontend correspond à tout ce que l’utilisateur voit et manipule.

* **Technologie** : JavaFX
* **Rôle** : affichage des écrans, gestion des interactions utilisateur

#### Composants principaux

1. **Vues (`.fxml`)**

   * Localisation : `src/main/resources/fxml/`
   * Rôle : définir la structure graphique des écrans (`login.fxml`, `dashboard.fxml`, etc.)

2. **Contrôleurs JavaFX**

   * Localisation : `src/main/java/com/senegalsante/controller/javafx/`
   * Rôle :

     * gérer les actions utilisateur (clics, saisies),
     * appeler la logique métier,
     * mettre à jour l’interface.

---

### Le Backend (Spring Boot) : le moteur de l’application

Le Backend contient toute la **logique métier** et la **gestion des données**, orchestrées par Spring Boot.

* **Technologie** : Spring Boot
* **Rôle** : traitement des données, règles métier, persistance

#### Architecture en couches

1. **Services (`@Service`)**

   * Localisation : `src/main/java/com/senegalsante/service/`
   * Contiennent la logique métier principale (inscription, création d’ordonnance, etc.).

2. **Repositories (`JpaRepository`)**

   * Localisation : `src/main/java/com/senegalsante/repository/`
   * Assurent l’accès à la base de données via Spring Data JPA, sans SQL explicite.

3. **Modèles (`@Entity`)**

   * Localisation : `src/main/java/com/senegalsante/model/`
   * Représentent les tables de la base SQLite.

---

### Communication Frontend–Backend : l’injection de dépendances

La communication entre JavaFX et Spring Boot repose sur **l’injection de dépendances**.

1. Les contrôleurs JavaFX sont déclarés comme composants Spring (`@Component`).
2. Les services ou repositories sont injectés via `@Autowired`.
3. Spring fournit automatiquement les instances nécessaires.

👉 Résultat : **faible couplage** et meilleure testabilité.

---

### Exemple concret : cycle de connexion

1. L’utilisateur clique sur **Se connecter** (vue `login.fxml`).
2. La méthode `handleLogin()` du `LoginController` est déclenchée.
3. Le contrôleur appelle `UserRepository.findByEmail(...)`.
4. Spring Data JPA interroge la base SQLite.
5. L’utilisateur est authentifié et le tableau de bord (`dashboard.fxml`) est affiché.

---

## 4. Technologies utilisées

* **Langage** : Java 17
* **Backend** : Spring Boot 3.2.0
* **Frontend** : JavaFX (OpenJFX 21.0.1)
* **ORM** : Hibernate / Spring Data JPA
* **Base de données** : SQLite
* **Build** : Apache Maven

---

## 5. Démarrage rapide

### Pré-requis

* JDK 17 ou supérieur
* Apache Maven 3.x ou supérieur

### Installation

```bash
git clone <URL_DU_DEPOT>
cd CARNET_SANTE_GITHUB
mvn clean install
```

### Lancer l’application

```bash
mvn clean javafx:run
```

### Générer l’exécutable JAR

```bash
mvn clean package
java -jar target/carnet-sante-1.0.0.jar
```

---

## 6. Gestion de la base de données

* Base **SQLite** locale (100 % hors ligne)
* Fichier généré automatiquement : `senegal_sante.db`
* Mise à jour automatique du schéma via :

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## 7. Contribution

Les contributions sont encouragées :

1. Fork du dépôt
2. Création d’une branche dédiée
3. Soumission d’une Pull Request

---

## 8. Licence

Ce projet est distribué sous licence **MIT**.

---

> **Note** : pensez à remplacer l’URL du dépôt GitHub par l’adresse réelle.
