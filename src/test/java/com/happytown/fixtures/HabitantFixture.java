package com.happytown.fixtures;

import com.happytown.core.entities.Habitant;

import java.time.LocalDate;
import java.util.UUID;

public class HabitantFixture {

    public static Habitant aHabitant() {
        String id = UUID.randomUUID().toString();
        String nom = "Carin";
        String prenom = "Marie";
        String email = "marie.carin@example.fr";
        LocalDate dateNaissance = LocalDate.of(1980, 10, 8);
        LocalDate dateArriveeCommune = LocalDate.of(2016, 12, 1);
        String adressePostale = "12 rue des Lilas";
        return new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
    }

}
