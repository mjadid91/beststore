package com.boostmytool.beststore.models;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class ProduitDto {
    @NotEmpty(message = "Le nom du produit est obligatoire")
    private String nom;

    @NotEmpty(message = "La marque du produit est obligatoire")
    private String marque;

    @NotEmpty(message = "La catégorie du produit est obligatoire")
    private String categorie;

    @Min(0)
    private double prix;

    @Size(min = 10, message = "La description doit contenir au moins 10 caractères")
    @Size(max = 2000, message = "La description ne doit pas dépasser 2000 caractères")
    private String description;

    private MultipartFile fichierImage;

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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public MultipartFile getFichierImage() {
        return fichierImage;
    }

    public void setFichierImage(MultipartFile fichierImage) {
        this.fichierImage = fichierImage;
    }
}
