package com.hamkitsi.mentalhealth.repository;

import com.hamkitsi.mentalhealth.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}