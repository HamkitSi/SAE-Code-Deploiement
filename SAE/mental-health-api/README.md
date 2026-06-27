# MindCare API

MindCare est une API REST développée en **Java avec Spring Boot**.
Elle a pour objectif de proposer un outil simple de suivi du bien-être mental, notamment pour les étudiants.

L’application permet de gérer :

* des utilisateurs ;
* un profil de bien-être personnel ;
* un journal d’humeur ;
* des exercices de relaxation ;
* des catégories d’exercices.

> Ce projet ne remplace pas un avis médical.
> Il s’agit uniquement d’un outil de suivi personnel et de sensibilisation au bien-être.

---

## Contexte du projet

Ce projet a été réalisé dans le cadre de la SAE :

**Développement & Déploiement d’une Application Web RESTful Conteneurisée**

L’objectif est de concevoir, développer, tester et déployer une application backend exposant une API RESTful, avec persistance des données dans une base relationnelle, utilisation d’un ORM, conteneurisation avec Docker et orchestration avec Docker Compose.

---

## Technologies utilisées

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL
* Docker
* Docker Compose
* Maven
* Postman

---

## Fonctionnalités principales

### Utilisateurs

L’API permet de créer, consulter, modifier et supprimer des utilisateurs.

Exemple d’utilisateur :

```json
{
  "nom": "Hasina",
  "email": "hasina@example.com"
}
```

---

### Profil bien-être

Chaque utilisateur peut avoir un profil bien-être contenant par exemple :

* un objectif personnel ;
* un rythme de sommeil.

Exemple :

```json
{
  "objectif": "Mieux gérer mon stress et améliorer mon sommeil",
  "rythmeSommeil": "En amélioration"
}
```

---

### Journal d’humeur

Un utilisateur peut enregistrer plusieurs entrées dans son journal d’humeur.

Chaque entrée contient :

* une date ;
* une humeur ;
* un niveau de stress ;
* un niveau d’énergie ;
* une note personnelle.

Exemple :

```json
{
  "date": "2026-06-27",
  "humeur": "stressé",
  "niveauStress": 7,
  "niveauEnergie": 4,
  "note": "Journée chargée, besoin de repos"
}
```

---

### Exercices de relaxation

L’API permet aussi de gérer des exercices de bien-être.

Exemple :

```json
{
  "titre": "Respiration 4-7-8",
  "description": "Inspirer 4 secondes, retenir 7 secondes, expirer 8 secondes.",
  "dureeMinutes": 5
}
```

---

### Catégories

Les exercices peuvent être associés à plusieurs catégories.

Exemples :

```json
{
  "nom": "Stress"
}
```

```json
{
  "nom": "Sommeil"
}
```

---

## Modélisation des relations

Le projet utilise JPA/Hibernate pour représenter les relations entre les entités Java et les tables de la base PostgreSQL.

---

### Relation One-to-One

Un utilisateur possède un seul profil bien-être.

```text
Utilisateur 1 --- 1 ProfilBienEtre
```

Exemple :

```text
Hasina --- Profil : mieux gérer le stress
```

---

### Relation One-to-Many / Many-to-One

Un utilisateur peut avoir plusieurs entrées dans son journal d’humeur.

```text
Utilisateur 1 --- * JournalHumeur
```

Exemple :

```text
Hasina
├── humeur du 26/06
├── humeur du 27/06
└── humeur du 28/06
```

---

### Relation Many-to-Many

Un exercice peut appartenir à plusieurs catégories, et une catégorie peut contenir plusieurs exercices.

```text
Exercice * --- * Categorie
```

Exemple :

```text
Respiration 4-7-8
├── Stress
└── Sommeil
```

---

## Architecture du projet

```text
mental-health-api/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/hamkitsi/mentalhealth/
│       │       ├── controller/
│       │       │   ├── StatusController.java
│       │       │   ├── UtilisateurController.java
│       │       │   ├── ProfilBienEtreController.java
│       │       │   ├── JournalHumeurController.java
│       │       │   ├── CategorieController.java
│       │       │   └── ExerciceController.java
│       │       ├── entity/
│       │       │   ├── Utilisateur.java
│       │       │   ├── ProfilBienEtre.java
│       │       │   ├── JournalHumeur.java
│       │       │   ├── Categorie.java
│       │       │   └── Exercice.java
│       │       ├── repository/
│       │       │   ├── UtilisateurRepository.java
│       │       │   ├── ProfilBienEtreRepository.java
│       │       │   ├── JournalHumeurRepository.java
│       │       │   ├── CategorieRepository.java
│       │       │   └── ExerciceRepository.java
│       │       └── MentalHealthApiApplication.java
│       └── resources/
│           ├── application.properties
│           └── data.sql
├── Dockerfile
├── docker-compose.yml
├── data.sql
├── pom.xml
└── README.md
```

---

## Lancer le projet avec Docker Compose

### Prérequis

Avant de lancer le projet, il faut avoir installé :

* Docker Desktop
* Docker Compose

---

### Lancement

Depuis la racine du projet, exécuter :

```bash
docker compose up --build
```

Cette commande lance :

* le conteneur de l’API Spring Boot ;
* le conteneur PostgreSQL ;
* le réseau Docker entre l’API et la base ;
* le volume Docker pour conserver les données.

---

### Accès à l’API

L’API est disponible à l’adresse :

```text
http://localhost:8080
```

Route de test :

```http
GET http://localhost:8080/api/status
```

Réponse attendue :

```text
MindCare API fonctionne
```

---

### Accès à PostgreSQL

La base PostgreSQL tourne dans Docker.

Configuration utilisée :

```text
Base de données : mental_health_db
Utilisateur     : mental
Mot de passe    : mental
Port local      : 5433
Port Docker     : 5432
```

---

### Arrêter les conteneurs

```bash
docker compose down
```

---

### Arrêter les conteneurs et supprimer le volume

Attention : cette commande supprime les données enregistrées.

```bash
docker compose down -v
```

---

## Configuration de l’application

Le fichier `application.properties` contient la configuration suivante :

```properties
spring.application.name=mental-health-api

spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5433/mental_health_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:mental}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:mental}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

server.port=8080
```

Cette configuration permet :

* de lancer l’application en local avec IntelliJ ;
* de lancer l’application dans Docker Compose avec les variables d’environnement.

---

## Exemples de routes REST

---

## Status

### Vérifier que l’API fonctionne

```http
GET /api/status
```

URL complète :

```text
http://localhost:8080/api/status
```

---

## Utilisateurs

### Récupérer tous les utilisateurs

```http
GET /api/utilisateurs
```

URL complète :

```text
http://localhost:8080/api/utilisateurs
```

---

### Récupérer un utilisateur par id

```http
GET /api/utilisateurs/{id}
```

Exemple :

```text
http://localhost:8080/api/utilisateurs/2
```

---

### Créer un utilisateur

```http
POST /api/utilisateurs
```

Body JSON :

```json
{
  "nom": "Hasina",
  "email": "hasina@example.com"
}
```

---

### Modifier un utilisateur

```http
PUT /api/utilisateurs/{id}
```

Exemple :

```text
http://localhost:8080/api/utilisateurs/2
```

Body JSON :

```json
{
  "nom": "Hasina Modifié",
  "email": "hasina.modifie@example.com"
}
```

---

### Supprimer un utilisateur

```http
DELETE /api/utilisateurs/{id}
```

Exemple :

```text
http://localhost:8080/api/utilisateurs/2
```

---

## Profils bien-être

### Récupérer tous les profils

```http
GET /api/profils
```

---

### Récupérer un profil par id

```http
GET /api/profils/{id}
```

---

### Créer un profil pour un utilisateur

```http
POST /api/profils/utilisateur/{utilisateurId}
```

Exemple :

```text
http://localhost:8080/api/profils/utilisateur/2
```

Body JSON :

```json
{
  "objectif": "Réduire le stress pendant les périodes de cours",
  "rythmeSommeil": "Irrégulier"
}
```

---

### Modifier un profil

```http
PUT /api/profils/{id}
```

Body JSON :

```json
{
  "objectif": "Mieux gérer mon stress et améliorer mon sommeil",
  "rythmeSommeil": "En amélioration"
}
```

---

### Supprimer un profil

```http
DELETE /api/profils/{id}
```

---

## Journal d’humeur

### Récupérer toutes les humeurs

```http
GET /api/humeurs
```

---

### Récupérer une humeur par id

```http
GET /api/humeurs/{id}
```

---

### Créer une humeur pour un utilisateur

```http
POST /api/humeurs/utilisateur/{utilisateurId}
```

Exemple :

```text
http://localhost:8080/api/humeurs/utilisateur/2
```

Body JSON :

```json
{
  "date": "2026-06-27",
  "humeur": "stressé",
  "niveauStress": 7,
  "niveauEnergie": 4,
  "note": "Journée chargée, besoin de repos"
}
```

---

### Modifier une humeur

```http
PUT /api/humeurs/{id}
```

Body JSON :

```json
{
  "date": "2026-06-27",
  "humeur": "apaisé",
  "niveauStress": 3,
  "niveauEnergie": 7,
  "note": "J’ai fait une pause et je me sens mieux"
}
```

---

### Supprimer une humeur

```http
DELETE /api/humeurs/{id}
```

---

## Catégories

### Récupérer toutes les catégories

```http
GET /api/categories
```

---

### Créer une catégorie

```http
POST /api/categories
```

Body JSON :

```json
{
  "nom": "Stress"
}
```

Autre exemple :

```json
{
  "nom": "Sommeil"
}
```

---

### Modifier une catégorie

```http
PUT /api/categories/{id}
```

Body JSON :

```json
{
  "nom": "Relaxation"
}
```

---

### Supprimer une catégorie

```http
DELETE /api/categories/{id}
```

---

## Exercices

### Récupérer tous les exercices

```http
GET /api/exercices
```

---

### Récupérer un exercice par id

```http
GET /api/exercices/{id}
```

---

### Créer un exercice

```http
POST /api/exercices
```

Body JSON :

```json
{
  "titre": "Respiration 4-7-8",
  "description": "Inspirer 4 secondes, retenir 7 secondes, expirer 8 secondes.",
  "dureeMinutes": 5
}
```

---

### Modifier un exercice

```http
PUT /api/exercices/{id}
```

Body JSON :

```json
{
  "titre": "Respiration guidée",
  "description": "Exercice de respiration pour réduire le stress.",
  "dureeMinutes": 10
}
```

---

### Associer un exercice à une catégorie

```http
POST /api/exercices/{exerciceId}/categories/{categorieId}
```

Exemple :

```text
http://localhost:8080/api/exercices/1/categories/1
```

Cette route permet de créer la relation Many-to-Many entre un exercice et une catégorie.

---

### Supprimer un exercice

```http
DELETE /api/exercices/{id}
```

---

## Données de test

Un fichier `data.sql` est fourni avec un jeu de données minimal :

* un utilisateur de démonstration ;
* un profil bien-être ;
* une entrée de journal d’humeur ;
* deux catégories ;
* un exercice ;
* deux associations entre l’exercice et les catégories.

Exemple de données fournies :

```sql
INSERT INTO utilisateur (id, nom, email)
VALUES (100, 'Demo User', 'demo@mindcare.fr')
ON CONFLICT DO NOTHING;

INSERT INTO categorie (id, nom)
VALUES (100, 'Stress')
ON CONFLICT DO NOTHING;

INSERT INTO categorie (id, nom)
VALUES (101, 'Sommeil')
ON CONFLICT DO NOTHING;
```

---

## Dockerfile

Le projet contient un `Dockerfile` permettant de construire l’image Docker de l’API.

Il utilise une image Java 17 :

```dockerfile
FROM eclipse-temurin:17-jdk AS build
```

Puis il construit le projet Maven et lance le fichier `.jar`.

---

## Docker Compose

Le fichier `docker-compose.yml` lance deux services :

```text
db  : base PostgreSQL
api : application Spring Boot
```

La base de données utilise un volume Docker nommé :

```text
postgres_data
```

Ce volume permet de conserver les données même si les conteneurs sont arrêtés.

---

## Image Docker Hub

À compléter après publication de l’image Docker.

Lien de l’image :

```text
https://hub.docker.com/r/hamkitsi/mental-health-api
```

Commande pour récupérer l’image :

```bash
docker pull hamkitsi/mental-health-api
```

Commande pour lancer l’image seule :

```bash
docker run -p 8080:8080 hamkitsi/mental-health-api
```

> Remarque : pour fonctionner avec PostgreSQL, il est recommandé d’utiliser `docker-compose.yml`.

---

## Tests avec Postman

Les routes ont été testées avec Postman.

Exemples de tests réalisés :

* création d’un utilisateur ;
* consultation des utilisateurs ;
* modification d’un utilisateur ;
* suppression d’un utilisateur ;
* création d’un profil bien-être ;
* création d’une humeur ;
* modification d’une humeur ;
* suppression d’une humeur ;
* création de catégories ;
* création d’exercices ;
* association d’un exercice à plusieurs catégories.

---

## Auteurs

Projet réalisé par :

```text
Hasina RAKOTOARISON
```

---

## État du projet

Fonctionnalités terminées :

* API REST fonctionnelle
* CRUD utilisateurs
* CRUD profils bien-être
* CRUD journal d’humeur
* CRUD catégories
* CRUD exercices
* Relation One-to-One
* Relation One-to-Many / Many-to-One
* Relation Many-to-Many
* PostgreSQL avec Docker
* API conteneurisée avec Docker
* Orchestration avec Docker Compose
* Fichier SQL de données de test
* Documentation README
