package com.happytown.core.entities;

import java.math.BigDecimal;

public class Cadeau {

    private String reference;
    private String description;
    private BigDecimal montant;
    private TrancheAge trancheAge;

    public Cadeau(String reference, String description, BigDecimal montant, TrancheAge trancheAge) {
        this.reference = reference;
        this.description = description;
        this.montant = montant;
        this.trancheAge = trancheAge;
    }

    public String getDetail() {
        return this.description + " " +
                "(Montant : " + this.montant + "€ - " +
                "Référence : " + this.reference + ")";

    }

}
