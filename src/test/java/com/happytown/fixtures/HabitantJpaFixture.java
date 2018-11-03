package com.happytown.fixtures;

import com.happytown.infrastructure.database.HabitantJpa;

import java.time.LocalDate;
import java.util.UUID;

public class HabitantJpaFixture {

    public static HabitantJpa aHabitantJpaSansCadeau() {
        HabitantJpa habitantJpa = new HabitantJpa();
        habitantJpa.setId(UUID.randomUUID().toString());
        habitantJpa.setNom("Carin");
        habitantJpa.setPrenom("Marie");
        habitantJpa.setEmail("marie.carin@example.fr");
        habitantJpa.setDateNaissance(LocalDate.of(1980, 10, 8));
        habitantJpa.setDateArriveeCommune(LocalDate.of(2016, 12, 1));
        habitantJpa.setAdressePostale("12 rue des Lilas");
        return habitantJpa;
    }

    public static HabitantJpa aHabitantJpaAvecCadeau() {
        HabitantJpa habitantJpa = new HabitantJpa();
        habitantJpa.setId(UUID.randomUUID().toString());
        habitantJpa.setNom("Carin");
        habitantJpa.setPrenom("Marie");
        habitantJpa.setEmail("marie.carin@example.fr");
        habitantJpa.setDateNaissance(LocalDate.of(1980, 10, 8));
        habitantJpa.setDateArriveeCommune(LocalDate.of(2016, 12, 1));
        habitantJpa.setAdressePostale("12 rue des Lilas");
        habitantJpa.setCadeauOffert("Plateau pour canapé (Montant : 24.90€ - Référence : aa23c026)");
        habitantJpa.setDateAttributionCadeau(LocalDate.of(2018, 11, 1));
        return habitantJpa;
    }

}
