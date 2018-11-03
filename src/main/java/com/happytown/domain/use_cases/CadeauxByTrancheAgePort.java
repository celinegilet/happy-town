package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Cadeau;
import com.happytown.domain.entities.TrancheAge;

import java.util.List;
import java.util.Map;

public interface CadeauxByTrancheAgePort {

    Map<TrancheAge, List<Cadeau>> get() throws CadeauxByTrancheAgeException;

}
