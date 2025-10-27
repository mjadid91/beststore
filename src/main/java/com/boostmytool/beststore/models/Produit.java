package com.boostmytool.beststore.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String marque;
    private String categorie;
    private double prix;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Date dateCreation;
    private String nomFichierImage;

    public int getId() {
        return id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNomFichierImage() {
        return nomFichierImage;
    }

    public void setNomFichierImage(String nomFichierImage) {
        this.nomFichierImage = nomFichierImage;
    }
}
