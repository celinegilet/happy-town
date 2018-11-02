package com.happytown.core.use_cases;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.Habitant;
import com.happytown.core.entities.TrancheAge;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class AttribuerCadeaux {

    private final HabitantProvider habitantProvider;
    private final NotificationProvider notificationProvider;
    private final CadeauxByTrancheAgeProvider cadeauxByTrancheAgeProvider;
    private final CadeauRandom cadeauRandom;

    private Clock clock;

    public static final String PATH_TEMPLATE_MESSAGE_CADEAU = "src/main/resources/messageCadeau.txt";
    public static final String PATH_TEMPLATE_MESSAGE_RECAP_CADEAUX = "src/main/resources/messageRecapCadeaux.txt";

    public AttribuerCadeaux(HabitantProvider habitantProvider, NotificationProvider notificationProvider, CadeauxByTrancheAgeProvider cadeauxByTrancheAgeProvider, CadeauRandom cadeauRandom, Clock clock) {
        this.habitantProvider = habitantProvider;
        this.notificationProvider = notificationProvider;
        this.cadeauxByTrancheAgeProvider = cadeauxByTrancheAgeProvider;
        this.cadeauRandom = cadeauRandom;
        this.clock = clock;
    }

    public void execute() {

        LocalDate now = LocalDate.now(clock);

        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = cadeauxByTrancheAgeProvider.get();
        List<Habitant> habitantsEligibles = habitantProvider.getEligiblesCadeaux(now.minusYears(1));

        habitantsEligibles.forEach(
                habitantEligible -> attributionCadeau(habitantEligible, cadeauxByTrancheAge, now)
        );

        List<Habitant> habitantsAttributionCadeau = habitantsEligibles.stream()
                .filter(Habitant::hasCadeau)
                .collect(toList());

        habitantsAttributionCadeau.forEach(
                habitantAttributionCadeau -> {
                    envoiMessageCadeau(habitantAttributionCadeau);
                    habitantProvider.save(habitantAttributionCadeau);
                }
        );
        envoiMessageRecapCadeauxJournee(habitantsAttributionCadeau, now);
    }

    private void attributionCadeau(Habitant habitantEligible, Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge, LocalDate now) {
        Optional<TrancheAge> trancheAge = getTrancheAgeCadeauHabitant(now, habitantEligible, cadeauxByTrancheAge.keySet());
        if (trancheAge.isPresent()) {
            List<Cadeau> cadeauxPossibles = cadeauxByTrancheAge.get(trancheAge.get());
            Cadeau cadeau = cadeauRandom.get(cadeauxPossibles);
            habitantEligible.attribuerCadeau(cadeau.getDetail(), now);
        }
    }

    private Optional<TrancheAge> getTrancheAgeCadeauHabitant(LocalDate now, Habitant habitant, Set<TrancheAge> trancheAges) {
        Integer ageHabitant = Period.between(habitant.getDateNaissance(), now).getYears();
        return trancheAges.stream()
                .filter(trancheAge -> ageHabitant >= trancheAge.getAgeMin()
                        && ageHabitant < trancheAge.getAgeMax())
                .findFirst();
    }

    private void envoiMessageCadeau(Habitant habitant) {
        String to = habitant.getEmail();
        String subject = "Happy Birthday in HappyTown!";
        Map<String, String> templateArgs = new HashMap<>();
        templateArgs.put("prenom", habitant.getPrenom());
        templateArgs.put("nom", habitant.getNom());
        templateArgs.put("cadeau", habitant.getCadeauOffert());
        notificationProvider.notifier(to, subject, PATH_TEMPLATE_MESSAGE_CADEAU, templateArgs);
    }

    private void envoiMessageRecapCadeauxJournee(List<Habitant> habitantsAttributionCadeau, LocalDate now) {
        if (!habitantsAttributionCadeau.isEmpty()) {
            String to = "mairie+service-cadeau@happytown.com";
            String subject = String.format("%1$td/%1$tm/%1$tY", now) + " - Synthese des cadeaux pour envoi";
            String cadeaux = "";
            for (Habitant habitantAttributionCadeau : habitantsAttributionCadeau) {
                cadeaux += " \n* " + habitantAttributionCadeau.getPrenom() + " " + habitantAttributionCadeau.getNom() + " : " + habitantAttributionCadeau.getCadeauOffert();
            }
            Map<String, String> templateArgs = new HashMap<>();
            templateArgs.put("cadeaux", cadeaux);
            notificationProvider.notifier(to, subject, PATH_TEMPLATE_MESSAGE_RECAP_CADEAUX, templateArgs);
        }
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
