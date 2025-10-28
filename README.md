# 📦 BestStore : Gestion de Produits

**BestStore** est une application web full-stack légère, construite avec Spring Boot et Thymeleaf. L'objectif de ce projet est de démontrer la mise en œuvre d'opérations **CRUD** (Create, Read, Update, Delete) complètes pour la gestion d'un inventaire de produits.

L'application permet aux utilisateurs de lister, ajouter, modifier et supprimer des produits, avec une gestion incluant la validation des formulaires et l'upload d'images.

## ✨ Fonctionnalités

* **Accueil** : Une page de bienvenue simple.
* **Liste des Produits** : Affiche tous les produits de la base de données dans un tableau responsive.
* **Création de Produit** : Un formulaire pour ajouter un nouveau produit, incluant la validation des champs (nom, marque, prix...) et un upload d'image.
* **Modification de Produit** : Pré-remplit un formulaire avec les données d'un produit existant pour permettre sa mise à jour.
* **Suppression de Produit** : Permet de supprimer un produit de la base de données.

## 🛠️ Stack Technique

* **Backend** : Spring Boot 3
* **Framework** : Spring MVC
* **Accès aux Données** : Spring Data JPA (via `JpaRepository`)
* **Base de Données** : MySQL
* **Moteur de Template** : Thymeleaf
* **Frontend** : Bootstrap 5 & Bootstrap Icons
* **Validation** : `jakarta.validation` (utilisé dans `ProduitDto`)
* **Serveur** : Tomcat (embarqué)

## 🚀 Démarrage Rapide

### 1. Prérequis

* Java (JDK 17+)
* Maven
* Un serveur MySQL fonctionnel

### 2. Configuration

1.  **Clonez le dépôt**
    ```sh
    git clone [https://github.com/mjadid91/beststore.git](https://github.com/mjadid91/beststore.git)
    cd beststore
    ```

2.  **Configurez la base de données**
    * Assurez-vous que votre serveur MySQL est en cours d'exécution.
    * Créez une base de données : `CREATE DATABASE beststore;`
    * Modifiez le fichier `src/main/resources/application.properties` pour correspondre à vos identifiants MySQL (utilisateur et mot de passe).

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/beststore
    spring.datasource.username=root
    spring.datasource.password=VOTRE_MOT_DE_PASSE
    
    # S'assure que les tables sont créées/mises à jour au démarrage
    spring.jpa.hibernate.ddl-auto=update 
    ```

### 3. Lancement

Utilisez le wrapper Maven pour lancer l'application :

```sh
# Sur macOS/Linux
./mvnw spring-boot:run

# Sur Windows
mvnw.cmd spring-boot:run
```
L'application sera accessible à l'adresse : `http://localhost:8080`

## 📂 Structure du Projet
```
beststore/
├── public/
│   └── images/               # Destination des images de produits uploadées
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/boostmytool/beststore/
│   │   │       ├── controllers/      # Contrôleurs MVC (ProduitsController, MainController)
│   │   │       ├── models/           # Entités JPA (Produit) et DTOs (ProduitDto)
│   │   │       ├── services/         # Interfaces Spring Data (ProduitsRepository)
│   │   │       ├── BeststoreApplication.java # Point d'entrée de Spring Boot
│   │   │       └── MvcConfig.java          # Configuration pour servir les images uploadées
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css     # Fichier de styles CSS personnalisé
│   │       │
│   │       ├── templates/            # Fichiers de template Thymeleaf
│   │       │   ├── produits/
│   │       │   │   ├── CreerProduit.html    # Formulaire de création
│   │       │   │   ├── ModifierProduit.html # Formulaire d'édition
│   │       │   │   └── index.html           # Liste des produits
│   │       │   │
│   │       │   └── index.html            # Page d'accueil
│   │       │
│   │       └── application.properties    # Fichier de configuration (BDD, etc.)
│   │
│   └── test/
│       └── java/
│           └── com/boostmytool/beststore/
│               └── BeststoreApplicationTests.java # Tests de l'application
│
├── .gitignore                    # Fichiers et dossiers à ignorer par Git
├── mvnw                          # Wrapper Maven (pour Mac/Linux)
├── mvnw.cmd                      # Wrapper Maven (pour Windows)
└── pom.xml                       # Fichier de configuration du projet Maven
```