package com.happytown.core.entities;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static com.happytown.fixtures.TrancheAgeFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class TrancheAgeComparatorTest {

    TrancheAgeComparator trancheAgeComparator = new TrancheAgeComparator();

    @Test
    void comparatorByTrancheAge_shouldCompareByAgeMinAndAgeMax() {
        // When
        Set<TrancheAge> trancheAges = new TreeSet<>(trancheAgeComparator);
        trancheAges.add(trancheAge_10_15());
        trancheAges.add(trancheAge_20_30());
        trancheAges.add(trancheAge_6_10());
        trancheAges.add(trancheAge_3_6());
        trancheAges.add(trancheAge_15_20());
        trancheAges.add(trancheAge_60_150());
        trancheAges.add(trancheAge_0_3());
        trancheAges.add(trancheAge_30_40());
        trancheAges.add(trancheAge_50_60());
        trancheAges.add(trancheAge_40_50());

        // Then
        assertThat(trancheAges).containsExactly(
                trancheAge_0_3(), trancheAge_3_6(), trancheAge_6_10(), trancheAge_10_15(),
                trancheAge_15_20(), trancheAge_20_30(), trancheAge_30_40(),
                trancheAge_40_50(), trancheAge_50_60(), trancheAge_60_150());
    }
}