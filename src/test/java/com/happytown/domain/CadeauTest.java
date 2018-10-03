package com.happytown.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CadeauTest {

    @Test
    void getDetail() {
        // Given
        TrancheAge trancheAge6_10 = TrancheAge.builder().ageMin(6).ageMax(10).build();
        Cadeau cadeau = Cadeau.builder()
                .reference("dbe982da")
                .description("Puissance 4 voyage")
                .montant(BigDecimal.valueOf(7.99))
                .trancheAge(trancheAge6_10)
                .build();

        // When
        String detail = cadeau.getDetail();

        // Then
        assertThat(detail).isEqualTo("Puissance 4 voyage (Montant : 7.99€ - Référence : dbe982da)");
    }
}