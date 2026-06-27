package com.hamkitsi.mentalhealth.controller;

import com.hamkitsi.mentalhealth.entity.Categorie;
import com.hamkitsi.mentalhealth.repository.CategorieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieRepository categorieRepository;

    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @GetMapping
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @PostMapping
    public Categorie createCategorie(@RequestBody Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id,
                                                     @RequestBody Categorie categorieDetails) {
        return categorieRepository.findById(id)
                .map(categorie -> {
                    categorie.setNom(categorieDetails.getNom());
                    return ResponseEntity.ok(categorieRepository.save(categorie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        if (!categorieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}