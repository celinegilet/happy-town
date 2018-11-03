package com.happytown.fixtures;

import com.happytown.domain.entities.Habitant;

import java.time.LocalDate;
import java.util.UUID;

public class HabitantFixture {

    public static Habitant aHabitantSansCadeau() {
        String id = UUID.randomUUID().toString();
        String nom = "Carin";
        String prenom = "Marie";
        String email = "marie.carin@example.fr";
        LocalDate dateNaissance = LocalDate.of(1980, 10, 8);
        LocalDate dateArriveeCommune = LocalDate.of(2016, 12, 1);
        String adressePostale = "12 rue des Lilas";
        return new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
    }

    public static Habitant aHabitantAvecCadeau() {
        String id = UUID.randomUUID().toString();
        String nom = "Carin";
        String prenom = "Marie";
        String email = "marie.carin@example.fr";
        LocalDate dateNaissance = LocalDate.of(1980, 10, 8);
        LocalDate dateArriveeCommune = LocalDate.of(2016, 12, 1);
        String adressePostale = "12 rue des Lilas";
        Habitant habitant = new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
        String cadeauOffert = "Plateau pour canapé (Montant : 24.90€ - Référence : aa23c026)";
        LocalDate dateAttributionCadeau = LocalDate.of(2018, 11, 1);
        habitant.attribuerCadeau(
                cadeauOffert,
                dateAttributionCadeau);
        return habitant;
    }

}
