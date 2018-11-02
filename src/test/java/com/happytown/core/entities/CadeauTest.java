package com.happytown.core.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_6_10;
import static org.assertj.core.api.Assertions.assertThat;

class CadeauTest {

    @Test
    void getDetail() {
        // Given
        BigDecimal montant = BigDecimal.valueOf(7.99);
        String description = "Puissance 4 voyage";
        String reference = "dbe982da";
        Cadeau cadeau = new Cadeau(reference, description, montant, trancheAge_6_10());

        // When
        String detail = cadeau.getDetail();

        // Then
        assertThat(detail).isEqualTo("Puissance 4 voyage (Montant : 7.99€ - Référence : dbe982da)");
    }
}