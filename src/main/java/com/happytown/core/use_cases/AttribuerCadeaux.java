package com.happytown.core.use_cases;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.Habitant;
import com.happytown.core.entities.TrancheAge;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Component
public class AttribuerCadeaux {

    private final HabitantProvider habitantProvider;
    private final NotificationProvider notificationProvider;
    private final CadeauxByTrancheAgeProvider cadeauxByTrancheAgeProvider;
    private final Random random;
    private Clock clock;

    public AttribuerCadeaux(HabitantProvider habitantProvider, NotificationProvider notificationProvider, CadeauxByTrancheAgeProvider cadeauxByTrancheAgeProvider, Clock clock) {
        this.habitantProvider = habitantProvider;
        this.notificationProvider = notificationProvider;
        this.cadeauxByTrancheAgeProvider = cadeauxByTrancheAgeProvider;
        this.clock = clock;
        random = new Random();
    }

    public void execute() {
        LocalDate now = LocalDate.now(clock);
        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = cadeauxByTrancheAgeProvider.get();
        List<Habitant> habitantsEligibles = habitantProvider.getEligiblesCadeaux(now.minusYears(1));
        List<Habitant> habitantsAttributionCadeau = new ArrayList<>();

        for (Habitant habitant : habitantsEligibles) {
            Optional<TrancheAge> trancheAge = getTrancheAgeCadeau(now, habitant, cadeauxByTrancheAge.keySet());
            if (trancheAge.isPresent()) {
                List<Cadeau> cadeauxPossibles = cadeauxByTrancheAge.get(trancheAge.get());
                Cadeau randomCadeau = cadeauxPossibles.get(random.nextInt(cadeauxPossibles.size()));
                envoiMessage(habitant, randomCadeau);
                habitant.attribuerCadeau(randomCadeau.getDetail(), now);
                habitantProvider.save(habitant);
                habitantsAttributionCadeau.add(habitant);
            }
        }
        envoiMessageSyntheseCadeauxJournee(habitantsAttributionCadeau, now);
    }

    private Optional<TrancheAge> getTrancheAgeCadeau(LocalDate now, Habitant habitant, Set<TrancheAge> trancheAges) {
        Optional<TrancheAge> optTrancheAge = Optional.empty();
        Integer ageHabitant = Period.between(habitant.getDateNaissance(), now).getYears();
        for (TrancheAge trancheAge : trancheAges) {
            if (ageHabitant >= trancheAge.getAgeMin() && ageHabitant < trancheAge.getAgeMax()) {
                optTrancheAge = Optional.of(trancheAge);
            }
        }
        return optTrancheAge;
    }

    private void envoiMessage(Habitant habitant, Cadeau randomCadeau) {
        String subject = "Happy Birthday in HappyTown!";
        String beneficiaire = habitant.getEmail();
        String body = "Bonjour " + habitant.getPrenom() + " " + habitant.getNom() + ",";
        body += "\n\nFélicitations, pour fêter votre premier anniversaire dans notre merveilleuse ville HappyTown, la mairie a le plaisir de vous offrir un cadeau de bienvenue.";
        body += "\n\nVotre cadeau est : " + randomCadeau.getDetail();
        body += "\n\nL'équipe HappyTown";
        notificationProvider.notifier(beneficiaire, subject, body);
    }

    private void envoiMessageSyntheseCadeauxJournee(List<Habitant> habitantsAttributionCadeau, LocalDate now) {
        if (!habitantsAttributionCadeau.isEmpty()) {
            String subject = String.format("%1$td/%1$tm/%1$tY", now) + " - Synthese des cadeaux pour envoi";
            String beneficiaire = "mairie+service-cadeau@happytown.com";
            String body = "Bonjour,";
            body += "\n\nVoici la liste récapitulative des cadeaux du jour : ";
            for (Habitant habitantAttributionCadeau : habitantsAttributionCadeau) {
                body += " \n* " + habitantAttributionCadeau.getPrenom() + " " + habitantAttributionCadeau.getNom() + " : " + habitantAttributionCadeau.getCadeauOffert();
            }
            body += "\n\nMerci!";
            notificationProvider.notifier(beneficiaire, subject, body);
        }
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
