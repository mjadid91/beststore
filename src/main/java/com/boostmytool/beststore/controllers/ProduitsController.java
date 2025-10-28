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

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/produits")
public class ProduitsController {

    @Autowired
    private ProduitsRepository repo;

    @GetMapping({"", "/"})
    public String listeProduits(Model model) {
        List<Produit> produits = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("produits", produits);
        return "produits/index";
    }

    @GetMapping("/creer")
    public String afficherFormulaireCreationProduit(Model model) {
        ProduitDto produitDto = new ProduitDto();
        model.addAttribute("produitDto", produitDto);
        return "produits/CreerProduit";
    }

    @PostMapping("/creer")
    public String creerProduit(@Valid @ModelAttribute ProduitDto produitDto, BindingResult result) {

        if (produitDto.getFichierImage().isEmpty()) {
            result.addError(new FieldError("produitDto", "fichierImage", "L'image du produit est obligatoire"));
        }

        if (result.hasErrors()) {
            return "produits/CreerProduit";
        }

        MultipartFile image = produitDto.getFichierImage();
        Date date = new Date();
        String stockageImage = date.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Path.of(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + stockageImage), StandardCopyOption.REPLACE_EXISTING);
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

    // ---- Version PathVariable pour modification ----
    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModificationProduit(Model model, @PathVariable int id) {

        Produit produit = repo.findById(id).orElse(null);
        if (produit == null) {
            return "redirect:/produits";
        }

        model.addAttribute("produit", produit);

        ProduitDto produitDto = new ProduitDto();
        produitDto.setId(produit.getId());
        produitDto.setNom(produit.getNom());
        produitDto.setMarque(produit.getMarque());
        produitDto.setCategorie(produit.getCategorie());
        produitDto.setPrix(produit.getPrix());
        produitDto.setDescription(produit.getDescription());
        produitDto.setNomFichierImage(produit.getNomFichierImage());
        produitDto.setDateCreation(produit.getDateCreation());

        model.addAttribute("produitDto", produitDto);

        return "produits/ModifierProduit";
    }

    @PostMapping("/modifier/{id}")
    public String modifierProduit(@PathVariable int id,
                                  @Valid @ModelAttribute ProduitDto produitDto,
                                  BindingResult result) {

        Produit produit = repo.findById(id).orElse(null);
        if (produit == null) {
            return "redirect:/produits";
        }

        if (result.hasErrors()) {
            return "produits/ModifierProduit";
        }

        // si une nouvelle image est uploadée
        if (produitDto.getFichierImage() != null && !produitDto.getFichierImage().isEmpty()) {
            MultipartFile image = produitDto.getFichierImage();
            String stockageImage = new Date().getTime() + "_" + image.getOriginalFilename();

            try {
                String uploadDir = "public/images/";
                Path uploadPath = Path.of(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + stockageImage), StandardCopyOption.REPLACE_EXISTING);
                }
                produit.setNomFichierImage(stockageImage);

            } catch (Exception e) {
                System.out.println("Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }

        produit.setNom(produitDto.getNom());
        produit.setMarque(produitDto.getMarque());
        produit.setCategorie(produitDto.getCategorie());
        produit.setPrix(produitDto.getPrix());
        produit.setDescription(produitDto.getDescription());

        repo.save(produit);

        return "redirect:/produits";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerProduit(@PathVariable int id) {
        Produit produit = repo.findById(id).orElse(null);
        if (produit != null) {
            repo.delete(produit);
        }
        return "redirect:/produits";
    }
}
