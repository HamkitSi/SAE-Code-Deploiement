package com.hamkitsi.mentalhealth.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class JournalHumeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String humeur;

    private int niveauStress;

    private int niveauEnergie;

    private String note;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public JournalHumeur() {
    }

    public JournalHumeur(LocalDate date, String humeur, int niveauStress, int niveauEnergie, String note, Utilisateur utilisateur) {
        this.date = date;
        this.humeur = humeur;
        this.niveauStress = niveauStress;
        this.niveauEnergie = niveauEnergie;
        this.note = note;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHumeur() {
        return humeur;
    }

    public void setHumeur(String humeur) {
        this.humeur = humeur;
    }

    public int getNiveauStress() {
        return niveauStress;
    }

    public void setNiveauStress(int niveauStress) {
        this.niveauStress = niveauStress;
    }

    public int getNiveauEnergie() {
        return niveauEnergie;
    }

    public void setNiveauEnergie(int niveauEnergie) {
        this.niveauEnergie = niveauEnergie;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}