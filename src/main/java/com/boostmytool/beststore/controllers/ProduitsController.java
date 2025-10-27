package com.boostmytool.beststore.controllers;

import com.boostmytool.beststore.models.Produit;
import com.boostmytool.beststore.models.ProduitDto;
import com.boostmytool.beststore.services.ProduitsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

// cette classe permet de gérer les requêtes HTTP liées aux produits

@Controller
@RequestMapping("/produits")
public class ProduitsController {

    @Autowired // Autowired signifie : Spring va injecter automatiquement une instance de ProduitsRepository ici
    private ProduitsRepository repo;

    @GetMapping({"", "/"})
    public String listeProduits(Model model) {
        List<Produit> produits = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("produits", produits);
        return "produits/index"; // retourne la vue produits/liste.html
    }

    @GetMapping("/creer")
    public String afficherFormulaireCreationProduit(Model model) {
        ProduitDto produitDto = new ProduitDto();
        model.addAttribute("produitDto", produitDto);
        return "produits/CreerProduit"; // retourne la vue produits/CreerProduit.html
    }

    @PostMapping("/creer")
    public String creerProduit(
            @Valid @ModelAttribute ProduitDto produitDto,
            BindingResult result) {

        if (produitDto.getFichierImage().isEmpty()) {
            result.addError(new FieldError("produitDto", "fichierImage", "L'image du produit est obligatoire"));
        }

        if (result.hasErrors()) {
            return "produits/CreerProduit";
        }

        MultipartFile image = produitDto.getFichierImage(); // on prend le fichier que l’utilisateur a uploadé dans le formulaire.
        Date date = new Date(); // On utilise la date actuelle en milliseconde
        String stockageImage = date.getTime() + "_" + image.getOriginalFilename(); // le nom original du fichier pour éviter que deux fichiers aient le même nom.

        try {
            String uploadDir = "public/images/"; // le dossier où l’on va stocker les images
            Path uploadPath = Path.of(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // on crée le dossier s’il n’existe pas
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + stockageImage), StandardCopyOption.REPLACE_EXISTING);
                // On ouvre le fichier temporaire envoyé par l’utilisateur et on le copie dans notre dossier public/images/ avec le nom qu’on a défini.
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'upload de l'image : " + e.getMessage());
        }

        Produit produit = new Produit();
        produit.setNom(produitDto.getNom());
        produit.setMarque(produitDto.getMarque());
        produit.setCategorie(produitDto.getCategorie());
        produit.setPrix(produitDto.getPrix());
        produit.setDescription(produitDto.getDescription());
        produit.setDateCreation(date);
        produit.setNomFichierImage(stockageImage);

        repo.save(produit);

        return "redirect:/produits";
    }

    @GetMapping("/modifier")
    public String afficherFormulaireModificationProduit(Model model, @RequestParam int id) {

        try {
            Produit produit = repo.findById(id).orElse(null); // on cherche le produit en base de données
            model.addAttribute("produit", produit);

            ProduitDto produitDto = new ProduitDto();
            produit.setNom(produit.getNom());
            produit.setMarque(produit.getMarque());
            produit.setCategorie(produit.getCategorie());
            produit.setPrix(produit.getPrix());
            produit.setDescription(produit.getDescription());

            model.addAttribute("produitDto", produitDto);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "redirect:/produits";
        }
        return "produits/ModifierProduit"; // retourne la vue produits/CreerProduit.html
    }
}
