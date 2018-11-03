package com.happytown.application.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@JsonPropertyOrder({"id", "nom", "prenom", "email", "dateNaissance", "dateArriveeCommune", "adressePostale", "cadeauOffert", "dateAttributionCadeau"})
public class HabitantApi {

    @ApiModelProperty(position = 0)
    private String id;

    @ApiModelProperty(position = 1)
    private String nom;

    @ApiModelProperty(position = 2)
    private String prenom;

    @ApiModelProperty(position = 3)
    private String email;

    @ApiModelProperty(position = 4)
    private LocalDate dateNaissance;

    @ApiModelProperty(position = 5)
    private LocalDate dateArriveeCommune;

    @ApiModelProperty(position = 6)
    private String adressePostale;

    @ApiModelProperty(position = 7)
    private String cadeauOffert;

    @ApiModelProperty(position = 8)
    private LocalDate dateAttributionCadeau;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateArriveeCommune() {
        return dateArriveeCommune;
    }

    public void setDateArriveeCommune(LocalDate dateArriveeCommune) {
        this.dateArriveeCommune = dateArriveeCommune;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getCadeauOffert() {
        return cadeauOffert;
    }

    public void setCadeauOffert(String cadeauOffert) {
        this.cadeauOffert = cadeauOffert;
    }

    public LocalDate getDateAttributionCadeau() {
        return dateAttributionCadeau;
    }

    public void setDateAttributionCadeau(LocalDate dateAttributionCadeau) {
        this.dateAttributionCadeau = dateAttributionCadeau;
    }
}
