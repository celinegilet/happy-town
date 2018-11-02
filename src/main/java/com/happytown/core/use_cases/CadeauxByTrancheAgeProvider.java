package com.happytown.core.use_cases;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.TrancheAge;

import java.util.List;
import java.util.Map;

public interface CadeauxByTrancheAgeProvider {

    Map<TrancheAge, List<Cadeau>> get() throws CadeauxByTrancheAgeException;

}
