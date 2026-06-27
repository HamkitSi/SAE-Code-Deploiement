package com.hamkitsi.mentalhealth.controller;

import com.hamkitsi.mentalhealth.entity.ProfilBienEtre;
import com.hamkitsi.mentalhealth.repository.ProfilBienEtreRepository;
import com.hamkitsi.mentalhealth.repository.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profils")
public class ProfilBienEtreController {

    private final ProfilBienEtreRepository profilBienEtreRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ProfilBienEtreController(ProfilBienEtreRepository profilBienEtreRepository,
                                    UtilisateurRepository utilisateurRepository) {
        this.profilBienEtreRepository = profilBienEtreRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping
    public List<ProfilBienEtre> getAllProfils() {
        return profilBienEtreRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfilBienEtre> getProfilById(@PathVariable Long id) {
        return profilBienEtreRepository.findById(id)
                .map(profil -> ResponseEntity.ok(profil))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<ProfilBienEtre> createProfil(@PathVariable Long utilisateurId,
                                                       @RequestBody ProfilBienEtre profilBienEtre) {
        return utilisateurRepository.findById(utilisateurId)
                .map(utilisateur -> {
                    profilBienEtre.setUtilisateur(utilisateur);
                    ProfilBienEtre saved = profilBienEtreRepository.save(profilBienEtre);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfilBienEtre> updateProfil(@PathVariable Long id,
                                                       @RequestBody ProfilBienEtre profilDetails) {
        return profilBienEtreRepository.findById(id)
                .map(profil -> {
                    profil.setObjectif(profilDetails.getObjectif());
                    profil.setRythmeSommeil(profilDetails.getRythmeSommeil());

                    ProfilBienEtre updated = profilBienEtreRepository.save(profil);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable Long id) {
        if (!profilBienEtreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        profilBienEtreRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}