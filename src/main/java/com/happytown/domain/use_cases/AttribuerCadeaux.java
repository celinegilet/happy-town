package com.happytown.domain.use_cases;

import com.happytown.domain.entities.Cadeau;
import com.happytown.domain.entities.Habitant;
import com.happytown.domain.entities.TrancheAge;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class AttribuerCadeaux {

    private final HabitantPort habitantPort;
    private final NotificationPort notificationPort;
    private final CadeauxByTrancheAgePort cadeauxByTrancheAgePort;
    private final CadeauRandom cadeauRandom;

    private static final String PATH_TEMPLATE_MESSAGE_CADEAU = "src/main/resources/messageCadeau.txt";
    private static final String PATH_TEMPLATE_MESSAGE_RECAP_CADEAUX = "src/main/resources/messageRecapCadeaux.txt";

    private Clock clock;

    public AttribuerCadeaux(HabitantPort habitantPort, NotificationPort notificationPort, CadeauxByTrancheAgePort cadeauxByTrancheAgePort, CadeauRandom cadeauRandom, Clock clock) {
        this.habitantPort = habitantPort;
        this.notificationPort = notificationPort;
        this.cadeauxByTrancheAgePort = cadeauxByTrancheAgePort;
        this.cadeauRandom = cadeauRandom;
        this.clock = clock;
    }

    public void execute() {

        LocalDate now = LocalDate.now(clock);

        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = cadeauxByTrancheAgePort.get();
        List<Habitant> habitantsEligibles = habitantPort.getEligiblesCadeaux(now.minusYears(1));

        habitantsEligibles.forEach(
                habitantEligible -> attributionCadeau(habitantEligible, cadeauxByTrancheAge, now)
        );

        List<Habitant> habitantsAttributionCadeau = habitantsEligibles.stream()
                .filter(Habitant::hasCadeau)
                .collect(toList());

        habitantsAttributionCadeau.forEach(
                habitantAttributionCadeau -> {
                    envoiMessageCadeau(habitantAttributionCadeau);
                    habitantPort.save(habitantAttributionCadeau);
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
        notificationPort.notifier(to, subject, PATH_TEMPLATE_MESSAGE_CADEAU, templateArgs);
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
            notificationPort.notifier(to, subject, PATH_TEMPLATE_MESSAGE_RECAP_CADEAUX, templateArgs);
        }
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
