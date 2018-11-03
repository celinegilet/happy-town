package com.happytown.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class TrancheAgeComparatorTest {

    TrancheAgeComparator trancheAgeComparator = new TrancheAgeComparator();

    @Test
    void comparatorByTrancheAge_shouldCompareByAgeMinAndAgeMax() {
        // Given
        TrancheAge trancheAge0_3 = new TrancheAge(0, 3);
        TrancheAge trancheAge3_6 = new TrancheAge(3, 6);
        TrancheAge trancheAge6_10 = new TrancheAge(6, 10);
        TrancheAge trancheAge10_15 = new TrancheAge(10, 15);
        TrancheAge trancheAge15_20 = new TrancheAge(15, 20);
        TrancheAge trancheAge20_30 = new TrancheAge(20, 30);
        TrancheAge trancheAge30_40 = new TrancheAge(30, 40);
        TrancheAge trancheAge40_50 = new TrancheAge(40, 50);
        TrancheAge trancheAge50_60 = new TrancheAge(50, 60);
        TrancheAge trancheAge60_150 = new TrancheAge(60, 150);

        // When
        Set<TrancheAge> trancheAges = new TreeSet<>(trancheAgeComparator);
        trancheAges.add(trancheAge10_15);
        trancheAges.add(trancheAge20_30);
        trancheAges.add(trancheAge6_10);
        trancheAges.add(trancheAge3_6);
        trancheAges.add(trancheAge15_20);
        trancheAges.add(trancheAge60_150);
        trancheAges.add(trancheAge0_3);
        trancheAges.add(trancheAge30_40);
        trancheAges.add(trancheAge50_60);
        trancheAges.add(trancheAge40_50);

        // Then
        assertThat(trancheAges).containsExactly(
                trancheAge0_3, trancheAge3_6, trancheAge6_10, trancheAge10_15,
                trancheAge15_20, trancheAge20_30, trancheAge30_40,
                trancheAge40_50, trancheAge50_60, trancheAge60_150);
    }
}