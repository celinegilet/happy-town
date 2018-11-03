package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Habitant;

import java.time.LocalDate;
import java.util.List;

public interface HabitantPort {

    List<Habitant> getAll();
    List<Habitant> getEligiblesCadeaux(LocalDate dateArriveeCommune);
    void save(Habitant habitant);

}
