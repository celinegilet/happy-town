package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Cadeau;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.happytown.fixtures.CadeauFixture.cadeauxTrancheAge_0_3;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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