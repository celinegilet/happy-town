package com.happytown.core.use_cases;

import com.happytown.core.entities.Cadeau;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.happytown.fixtures.CadeauFixture.cadeauxTrancheAge_0_3;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CadeauRandomTest {

    CadeauRandom cadeauRandom = new CadeauRandom();

    @Test
    void get() {
        // Given
        List<Cadeau> cadeaux = cadeauxTrancheAge_0_3();

        // When
        Cadeau cadeau = cadeauRandom.get(cadeaux);

        // Then
        assertThat(cadeau).isIn(cadeaux);
    }
}