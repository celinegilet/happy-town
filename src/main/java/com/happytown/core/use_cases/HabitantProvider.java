package com.happytown.core.use_cases;

import com.happytown.core.entities.Habitant;

import java.time.LocalDate;
import java.util.List;

public interface HabitantProvider {

    List<Habitant> getAll();

    List<Habitant> getEligiblesCadeaux(LocalDate dateArriveeCommune);

    void save(Habitant habitant);
}
