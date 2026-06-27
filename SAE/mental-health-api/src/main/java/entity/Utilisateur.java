package com.hamkitsi.mentalhealth.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String email;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private ProfilBienEtre profilBienEtre;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<JournalHumeur> journauxHumeur = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}