package com.happytown.fixtures;

import com.happytown.core.entities.Cadeau;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_0_3;
import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_10_15;

public class CadeauFixture {

    public static List<Cadeau> cadeauxTrancheAge_0_3() {
        List<Cadeau> cadeaux = new ArrayList<>();
        cadeaux.add(new Cadeau("70d73d02", "Peluche oggy et les cafards", BigDecimal.valueOf(11.99), trancheAge_0_3()));
        cadeaux.add(new Cadeau("c01c31a3", "Cheval floppy", BigDecimal.valueOf(14.99), trancheAge_0_3()));
        cadeaux.add(new Cadeau("fc02d2df", "Freddie la luciole", BigDecimal.valueOf(15.99), trancheAge_0_3()));
        cadeaux.add(new Cadeau("66418d5b", "La pieuvre d'activités", BigDecimal.valueOf(19.99), trancheAge_0_3()));
        cadeaux.add(new Cadeau("a3d832e5", "Peluche souris étoile", BigDecimal.valueOf(24.99), trancheAge_0_3()));
        return cadeaux;
    }

    public static List<Cadeau> cadeauxTrancheAge_10_15() {
        List<Cadeau> cadeaux = new ArrayList<>();
        cadeaux.add(new Cadeau("6890e222", "Puzzle 1000 pièces magnifique ville de new-york", BigDecimal.valueOf(14.99), trancheAge_10_15()));
        cadeaux.add(new Cadeau("95352fa2", "Kit kart hoverboard", BigDecimal.valueOf(39.99), trancheAge_10_15()));
        cadeaux.add(new Cadeau("15affe80", "Loup Garou", BigDecimal.valueOf(11.99), trancheAge_10_15()));
        cadeaux.add(new Cadeau("95013804", "Ma chimie super facile", BigDecimal.valueOf(9.99), trancheAge_10_15()));
        cadeaux.add(new Cadeau("30b183cf", "Colt express", BigDecimal.valueOf(29.99), trancheAge_10_15()));
        return cadeaux;
    }

}
