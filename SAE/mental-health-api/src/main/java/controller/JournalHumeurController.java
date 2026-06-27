package com.hamkitsi.mentalhealth.controller;

import com.hamkitsi.mentalhealth.entity.JournalHumeur;
import com.hamkitsi.mentalhealth.repository.JournalHumeurRepository;
import com.hamkitsi.mentalhealth.repository.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/humeurs")
public class JournalHumeurController {

    private final JournalHumeurRepository journalHumeurRepository;
    private final com.hamkitsi.mentalhealth.repository.UtilisateurRepository utilisateurRepository;

    public JournalHumeurController(JournalHumeurRepository journalHumeurRepository,
                                   UtilisateurRepository utilisateurRepository) {
        this.journalHumeurRepository = journalHumeurRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public List<JournalHumeur> getAllHumeurs() {
        return journalHumeurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalHumeur> getHumeurById(@PathVariable Long id) {
        return journalHumeurRepository.findById(id)
                .map(humeur -> ResponseEntity.ok(humeur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<JournalHumeur> createHumeur(@PathVariable Long utilisateurId,
                                                      @RequestBody JournalHumeur journalHumeur) {
        return utilisateurRepository.findById(utilisateurId)
                .map(utilisateur -> {
                    journalHumeur.setUtilisateur(utilisateur);
                    JournalHumeur saved = journalHumeurRepository.save(journalHumeur);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalHumeur> updateHumeur(@PathVariable Long id,
                                                      @RequestBody JournalHumeur humeurDetails) {
        return journalHumeurRepository.findById(id)
                .map(humeur -> {
                    humeur.setDate(humeurDetails.getDate());
                    humeur.setHumeur(humeurDetails.getHumeur());
                    humeur.setNiveauStress(humeurDetails.getNiveauStress());
                    humeur.setNiveauEnergie(humeurDetails.getNiveauEnergie());
                    humeur.setNote(humeurDetails.getNote());

                    JournalHumeur updated = journalHumeurRepository.save(humeur);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHumeur(@PathVariable Long id) {
        if (!journalHumeurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        journalHumeurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}