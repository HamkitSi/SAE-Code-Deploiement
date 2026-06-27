package com.hamkitsi.mentalhealth.controller;

import com.hamkitsi.mentalhealth.entity.Categorie;
import com.hamkitsi.mentalhealth.entity.Exercice;
import com.hamkitsi.mentalhealth.repository.CategorieRepository;
import com.hamkitsi.mentalhealth.repository.ExerciceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercices")
public class ExerciceController {

    private final ExerciceRepository exerciceRepository;
    private final CategorieRepository categorieRepository;

    public ExerciceController(ExerciceRepository exerciceRepository,
                              CategorieRepository categorieRepository) {
        this.exerciceRepository = exerciceRepository;
        this.categorieRepository = categorieRepository;
    }

    @GetMapping
    public List<Exercice> getAllExercices() {
        return exerciceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercice> getExerciceById(@PathVariable Long id) {
        return exerciceRepository.findById(id)
                .map(exercice -> ResponseEntity.ok(exercice))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Exercice createExercice(@RequestBody Exercice exercice) {
        return exerciceRepository.save(exercice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercice> updateExercice(@PathVariable Long id,
                                                   @RequestBody Exercice exerciceDetails) {
        return exerciceRepository.findById(id)
                .map(exercice -> {
                    exercice.setTitre(exerciceDetails.getTitre());
                    exercice.setDescription(exerciceDetails.getDescription());
                    exercice.setDureeMinutes(exerciceDetails.getDureeMinutes());

                    Exercice updated = exerciceRepository.save(exercice);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{exerciceId}/categories/{categorieId}")
    public ResponseEntity<Exercice> ajouterCategorieAExercice(@PathVariable Long exerciceId,
                                                              @PathVariable Long categorieId) {
        Optional<Exercice> exerciceOpt = exerciceRepository.findById(exerciceId);
        Optional<Categorie> categorieOpt = categorieRepository.findById(categorieId);

        if (exerciceOpt.isEmpty() || categorieOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Exercice exercice = exerciceOpt.get();
        Categorie categorie = categorieOpt.get();

        exercice.getCategories().add(categorie);

        Exercice saved = exerciceRepository.save(exercice);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercice(@PathVariable Long id) {
        if (!exerciceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        exerciceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}