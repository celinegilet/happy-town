package com.happytown.service;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.Habitant;
import com.happytown.core.entities.TrancheAge;
import com.happytown.core.use_cases.HabitantProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional
public class HappyTownService {

    private final HabitantProvider habitantProvider;
    private final Random random;

    public HappyTownService(HabitantProvider habitantProvider) {
        this.habitantProvider = habitantProvider;
        random = new Random();
    }

    public void attribuerCadeaux(String fileName, LocalDate dateCourante, String smtpHost, int smtpPort) throws IOException, MessagingException {

        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = buildCadeauxByTrancheAge(fileName);
        List<Habitant> habitantsEligibles = habitantProvider.getEligiblesCadeaux(dateCourante.minusYears(1));
        List<Habitant> habitantsAttributionCadeau = new ArrayList<>();

        for (Habitant habitant : habitantsEligibles) {
            Optional<TrancheAge> trancheAge = getTrancheAgeCadeau(dateCourante, habitant, cadeauxByTrancheAge.keySet());
            if (trancheAge.isPresent()) {
                List<Cadeau> cadeauxPossibles = cadeauxByTrancheAge.get(trancheAge.get());
                Cadeau randomCadeau = cadeauxPossibles.get(random.nextInt(cadeauxPossibles.size()));
                envoiMessage(smtpHost, smtpPort, habitant, randomCadeau);
                habitant.attribuerCadeau(randomCadeau.getDetail(), dateCourante);
                habitantProvider.save(habitant);
                habitantsAttributionCadeau.add(habitant);
            }
        }
        envoiMessageSyntheseCadeauxJournee(smtpHost, smtpPort, habitantsAttributionCadeau, dateCourante);
    }

    private Map<TrancheAge, List<Cadeau>> buildCadeauxByTrancheAge(String fileName) throws IOException {
        TrancheAgeComparator trancheAgeComparator = new TrancheAgeComparator();
        Map<TrancheAge, List<Cadeau>> cadeauxByTrancheAge = new TreeMap<>(trancheAgeComparator);
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        in.readLine();
        String line = "";
        while ((line = in.readLine()) != null) {
            String[] cadeauData = line.split(",");
            String reference = cadeauData[0];
            String description = cadeauData[1];
            BigDecimal montant = new BigDecimal(cadeauData[2]);
            String[] trancheAgeData = cadeauData[3].split("-");
            TrancheAge trancheAge = new TrancheAge(Integer.valueOf(trancheAgeData[0]), Integer.valueOf(trancheAgeData[1]));
            if (!cadeauxByTrancheAge.containsKey(trancheAge)) {
                cadeauxByTrancheAge.put(trancheAge, new ArrayList<>());
            }
            Cadeau cadeau = new Cadeau(reference, description, montant, trancheAge);
            cadeauxByTrancheAge.get(trancheAge).add(cadeau);
        }
        return cadeauxByTrancheAge;
    }

    private Optional<TrancheAge> getTrancheAgeCadeau(LocalDate dateCourante, Habitant habitant, Set<TrancheAge> trancheAges) {
        Optional<TrancheAge> optTrancheAge = Optional.empty();
        Integer ageHabitant = Period.between(habitant.getDateNaissance(), dateCourante).getYears();
        for (TrancheAge trancheAge : trancheAges) {
            if (ageHabitant >= trancheAge.getAgeMin() && ageHabitant < trancheAge.getAgeMax()) {
                optTrancheAge = Optional.of(trancheAge);
            }
        }
        return optTrancheAge;
    }

    private void envoiMessage(String smtpHost, int smtpPort, Habitant habitant, Cadeau randomCadeau) throws MessagingException {
        String subject = "Happy Birthday in HappyTown!";
        String beneficiaire = habitant.getEmail();
        String body = "Bonjour " + habitant.getPrenom() + " " + habitant.getNom() + ",";
        body += "\n\nFélicitations, pour fêter votre premier anniversaire dans notre merveilleuse ville HappyTown, la mairie a le plaisir de vous offrir un cadeau de bienvenue.";
        body += "\n\nVotre cadeau est : " + randomCadeau.getDetail();
        body += "\n\nL'équipe HappyTown";
        envoiMail(smtpHost, smtpPort, subject, beneficiaire, body);
    }

    private void envoiMessageSyntheseCadeauxJournee(String smtpHost, int smtpPort, List<Habitant> habitantsAttributionCadeau, LocalDate dateCourante) throws MessagingException {
        if (!habitantsAttributionCadeau.isEmpty()) {
            String subject = String.format("%1$td/%1$tm/%1$tY", dateCourante) + " - Synthese des cadeaux pour envoi";
            String beneficiaire = "mairie+service-cadeau@happytown.com";
            String body = "Bonjour,";
            body += "\n\nVoici la liste récapitulative des cadeaux du jour : ";
            for (Habitant habitantAttributionCadeau : habitantsAttributionCadeau) {
                body += " \n* " + habitantAttributionCadeau.getPrenom() + " " + habitantAttributionCadeau.getNom() + " : " + habitantAttributionCadeau.getCadeauOffert();
            }
            body += "\n\nMerci!";
            envoiMail(smtpHost, smtpPort, subject, beneficiaire, body);
        }
    }

    private void envoiMail(String smtpHost, int smtpPort, String subject, String beneficiaire, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "" + smtpPort);
        Session session = Session.getInstance(props, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mairie@happytown.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(beneficiaire));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
    }

}
