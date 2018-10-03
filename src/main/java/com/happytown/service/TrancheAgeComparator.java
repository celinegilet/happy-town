package com.happytown.service;

import com.happytown.domain.TrancheAge;

import java.util.Comparator;

public class TrancheAgeComparator implements Comparator<TrancheAge> {

    @Override
    public int compare(TrancheAge trancheAge1, TrancheAge trancheAge2) {
        Integer sumAnneesTanche1 = trancheAge1.getAgeMin() + trancheAge1.getAgeMax();
        Integer sumAnneesTranche2 = trancheAge2.getAgeMin() + trancheAge2.getAgeMax();
        return sumAnneesTanche1.compareTo(sumAnneesTranche2);
    }

}
