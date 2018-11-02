package com.happytown.dataproviders.file;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.TrancheAge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class CadeauxByTrancheAgeFileProviderTest {

    @Autowired
    CadeauxByTrancheAgeFileProvider cadeauxByTrancheAgeFileProvider;

    private static final TrancheAge TRANCHE_AGE_0_3 = new TrancheAge(0, 3);
    private static final List<Cadeau> CADEAUX_TRANCHE_AGE_0_3 = new ArrayList<Cadeau>() {{
        add(new Cadeau("70d73d02", "Peluche oggy et les cafards", BigDecimal.valueOf(11.99), TRANCHE_AGE_0_3));
        add(new Cadeau("c01c31a3", "Cheval floppy", BigDecimal.valueOf(14.99), TRANCHE_AGE_0_3));
        add(new Cadeau("fc02d2df", "Freddie la luciole", BigDecimal.valueOf(15.99), TRANCHE_AGE_0_3));
        add(new Cadeau("66418d5b", "La pieuvre d'activités", BigDecimal.valueOf(19.99), TRANCHE_AGE_0_3));
        add(new Cadeau("a3d832e5", "Peluche souris étoile", BigDecimal.valueOf(24.99), TRANCHE_AGE_0_3));
    }};

    private static final TrancheAge TRANCHE_AGE_10_15 = new TrancheAge(10, 15);
    private static final List<Cadeau> CADEAUX_TRANCHE_AGE_10_15 = new ArrayList<Cadeau>() {{
        add(new Cadeau("6890e222", "Puzzle 1000 pièces magnifique ville de new-york", BigDecimal.valueOf(14.99), TRANCHE_AGE_10_15));
        add(new Cadeau("95352fa2", "Kit kart hoverboard", BigDecimal.valueOf(39.99), TRANCHE_AGE_10_15));
        add(new Cadeau("15affe80", "Loup Garou", BigDecimal.valueOf(11.99), TRANCHE_AGE_10_15));
        add(new Cadeau("95013804", "Ma chimie super facile", BigDecimal.valueOf(9.99), TRANCHE_AGE_10_15));
        add(new Cadeau("30b183cf", "Colt express", BigDecimal.valueOf(29.99), TRANCHE_AGE_10_15));
    }};

    @Test
    void get_shouldReturnCadeauxByTrancheAgeFromFile() {
        // When
        Map<TrancheAge, List<Cadeau>> results = cadeauxByTrancheAgeFileProvider.get();

        // Then
        int nbTranchesAges = 10;
        assertThat(results).hasSize(nbTranchesAges);
        List<Cadeau> cadeauxTrancheAge0_3 = results.get(TRANCHE_AGE_0_3);
        assertThat(cadeauxTrancheAge0_3).containsExactlyElementsOf(CADEAUX_TRANCHE_AGE_0_3);
        List<Cadeau> cadeauxTrancheAge10_15 = results.get(TRANCHE_AGE_10_15);
        assertThat(cadeauxTrancheAge10_15).containsExactlyElementsOf(CADEAUX_TRANCHE_AGE_10_15);
    }
}