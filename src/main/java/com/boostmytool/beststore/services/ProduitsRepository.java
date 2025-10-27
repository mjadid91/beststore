package com.boostmytool.beststore.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boostmytool.beststore.models.Produit;

public interface ProduitsRepository extends JpaRepository<Produit, Integer> {
}
