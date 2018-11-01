package com.happytown.core.entities;

import java.time.LocalDate;

public class Habitant {

    private String id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private LocalDate dateArriveeCommune;
    private String adressePostale;
    private String cadeauOffert;
    private LocalDate dateAttributionCadeau;

    public Habitant(String id, String nom, String prenom, String email, LocalDate dateNaissance, LocalDate dateArriveeCommune, String adressePostale) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.dateArriveeCommune = dateArriveeCommune;
        this.adressePostale = adressePostale;
    }

    public void attribuerCadeau(String cadeauOffert, LocalDate dateAttributionCadeau) {
        this.cadeauOffert = cadeauOffert;
        this.dateAttributionCadeau = dateAttributionCadeau;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public LocalDate getDateArriveeCommune() {
        return dateArriveeCommune;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public String getCadeauOffert() {
        return cadeauOffert;
    }

    public LocalDate getDateAttributionCadeau() {
        return dateAttributionCadeau;
    }
}
