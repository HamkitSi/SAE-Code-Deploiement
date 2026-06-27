package com.hamkitsi.mentalhealth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final com.hamkitsi.mentalhealth.repository.UtilisateurRepository utilisateurRepository;

    public UtilisateurController(com.hamkitsi.mentalhealth.repository.UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public List<com.hamkitsi.mentalhealth.entity.Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.hamkitsi.mentalhealth.entity.Utilisateur> getUtilisateurById(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(utilisateur -> ResponseEntity.ok(utilisateur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public com.hamkitsi.mentalhealth.entity.Utilisateur createUtilisateur(@RequestBody com.hamkitsi.mentalhealth.entity.Utilisateur utilisateur) {
        return this.utilisateurRepository.save(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.hamkitsi.mentalhealth.entity.Utilisateur> updateUtilisateur(@PathVariable Long id,
                                                                                          @RequestBody com.hamkitsi.mentalhealth.entity.Utilisateur utilisateurDetails) {
        return utilisateurRepository.findById(id)
                .map(utilisateur -> {
                    utilisateur.setNom(utilisateurDetails.getNom());
                    utilisateur.setEmail(utilisateurDetails.getEmail());

                    com.hamkitsi.mentalhealth.entity.Utilisateur updatedUtilisateur = this.utilisateurRepository.save(utilisateur);
                    return ResponseEntity.ok(updatedUtilisateur);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (!utilisateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        utilisateurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}