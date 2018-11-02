package com.happytown.dataproviders.file;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.TrancheAge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static com.happytown.fixtures.CadeauFixture.cadeauxTrancheAge_0_3;
import static com.happytown.fixtures.CadeauFixture.cadeauxTrancheAge_10_15;
import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_0_3;
import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_10_15;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class CadeauxByTrancheAgeFileProviderTest {

    @Autowired
    CadeauxByTrancheAgeFileProvider cadeauxByTrancheAgeFileProvider;

    @Test
    void get_shouldReturnCadeauxByTrancheAgeFromFile() {
        // When
        Map<TrancheAge, List<Cadeau>> results = cadeauxByTrancheAgeFileProvider.get();

        // Then
        int nbTranchesAges = 10;
        assertThat(results).hasSize(nbTranchesAges);
        List<Cadeau> cadeauxTrancheAge0_3 = results.get(trancheAge_0_3());
        assertThat(cadeauxTrancheAge0_3).containsExactlyElementsOf(cadeauxTrancheAge_0_3());
        List<Cadeau> cadeauxTrancheAge10_15 = results.get(trancheAge_10_15());
        assertThat(cadeauxTrancheAge10_15).containsExactlyElementsOf(cadeauxTrancheAge_10_15());
    }
}