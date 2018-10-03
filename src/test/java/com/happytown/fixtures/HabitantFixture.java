package com.happytown.fixtures;

import com.happytown.domain.Habitant;

import java.time.LocalDate;
import java.util.UUID;

public class HabitantFixture {

    public static Habitant aHabitant() {
        return Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Carin")
                .prenom("Marie")
                .adressePostale("12 rue des Lilas")
                .email("marie.carin@example.fr")
                .dateNaissance(LocalDate.of(1980, 10, 8))
                .dateArriveeCommune(LocalDate.of(2016, 12, 1))
                .build();
    }

}
