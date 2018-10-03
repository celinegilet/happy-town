package com.happytown.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cadeau {

    @NotBlank
    private String reference;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal montant;
    @NotNull
    private TrancheAge trancheAge;

    public String getDetail() {
        return this.description + " " +
                "(Montant : " + this.montant + "€ - " +
                "Référence : " + this.reference + ")";

    }

}
