package com.hamkitsi.mentalhealth.entity;

import jakarta.persistence.*;

@Entity
public class ProfilBienEtre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectif;

    private String rythmeSommeil;

    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public ProfilBienEtre() {
    }

    public ProfilBienEtre(String objectif, String rythmeSommeil, Utilisateur utilisateur) {
        this.objectif = objectif;
        this.rythmeSommeil = rythmeSommeil;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getRythmeSommeil() {
        return rythmeSommeil;
    }

    public void setRythmeSommeil(String rythmeSommeil) {
        this.rythmeSommeil = rythmeSommeil;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}