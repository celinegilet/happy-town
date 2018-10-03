package com.happytown.service;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import com.happytown.domain.Habitant;
import com.happytown.repository.HabitantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HappyTownServiceTest {

    @InjectMocks
    HappyTownService happyTownService;

    @Mock
    HabitantRepository habitantRepository;

    private SimpleSmtpServer mailServer;

    private static final int SMTP_PORT = 9999;
    private static final String SMTP_HOST = "localhost";
    private static final String FILE_NAME = "src/main/resources/cadeaux.txt";
    private static final LocalDate NOW = LocalDate.of(2018, 10, 1);
    private static final LocalDate NOW_MINUS_ONE_YEAR = LocalDate.of(2017, 10, 1);

    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_0_3 = Pattern.compile("70d73d02|c01c31a3|fc02d2df|66418d5b|a3d832e5");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_3_6 = Pattern.compile("b3f83de3|6a52d970|2287ae90|a3ba8f33|b37eb259");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_6_10 = Pattern.compile("2ee03dac|dbe982da|eae0871f|b96c5bb7|7db6a02f");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_10_15 = Pattern.compile("6890e222|95352fa2|15affe80|95013804|30b183cf");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_15_20 = Pattern.compile("cae67bbb|225973d8|6f7c3c97|001f1a3f|268594d7");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_20_30 = Pattern.compile("5bd74b84|14cfe629|29e1b862|07eb02d3|3d1248c5");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_30_40 = Pattern.compile("aa23c026|6a64d9e7|861d1d35|37c88b3c|d9b68019");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_40_50 = Pattern.compile("dd1954e8|a40d837a|40a88a96|6e40b52d|7c5e8641");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_50_60 = Pattern.compile("f14f767d|9393cf65|6082f1f6|e72cfae4|7b22a16f");
    private static final Pattern REGEX_REF_CADEAUX_TRANCHE_AGE_60_150 = Pattern.compile("b9dcca0d|90a2efeb|67f53023|0200ddd6|d9860e8d");

    @BeforeEach
    void setUp() throws InterruptedException {
        mailServer = SimpleSmtpServer.start(SMTP_PORT);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        mailServer.stop();
        Thread.sleep(2000);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge0_3() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Paron")
                .prenom("Elise")
                .adressePostale("48 faubourg de la Plage")
                .email("elise.paron@example.fr")
                .dateNaissance(LocalDate.of(2018, 6, 22))
                .dateArriveeCommune(LocalDate.of(2017, 5, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("elise.paron@example.fr", "Elise Paron", REGEX_REF_CADEAUX_TRANCHE_AGE_0_3);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_0_3);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge3_6() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Giron")
                .prenom("Manon")
                .adressePostale("2 rue des Apotres")
                .email("manon.giron@example.fr")
                .dateNaissance(LocalDate.of(2012, 10, 2))
                .dateArriveeCommune(LocalDate.of(2017, 5, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("manon.giron@example.fr", "Manon Giron", REGEX_REF_CADEAUX_TRANCHE_AGE_3_6);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_3_6);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge6_10() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Perraud")
                .prenom("Lucas")
                .adressePostale("17 boulevard des Capucines")
                .email("lucas.perraud@example.fr")
                .dateNaissance(LocalDate.of(2011, 4, 4))
                .dateArriveeCommune(LocalDate.of(2017, 9, 10))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("lucas.perraud@example.fr", "Lucas Perraud", REGEX_REF_CADEAUX_TRANCHE_AGE_6_10);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_6_10);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge10_15() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Leduc")
                .prenom("Etienne")
                .adressePostale("28 square du Bois Fleuri")
                .email("etienne.leduc@example.fr")
                .dateNaissance(LocalDate.of(2006, 5, 14))
                .dateArriveeCommune(LocalDate.of(2017, 9, 10))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("etienne.leduc@example.fr", "Etienne Leduc", REGEX_REF_CADEAUX_TRANCHE_AGE_10_15);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_10_15);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge15_20() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Guilbaud")
                .prenom("Elodie")
                .adressePostale("1 impasse du Cheval Blanc")
                .email("elodie.guilbaud@example.fr")
                .dateNaissance(LocalDate.of(1998, 10, 2))
                .dateArriveeCommune(LocalDate.of(2017, 10, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("elodie.guilbaud@example.fr", "Elodie Guilbaud", REGEX_REF_CADEAUX_TRANCHE_AGE_15_20);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_15_20);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge20_30() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Newman")
                .prenom("Paul")
                .adressePostale("14 chemin Edmond Rostand")
                .email("paul.newman@example.fr")
                .dateNaissance(LocalDate.of(1998, 10, 1))
                .dateArriveeCommune(LocalDate.of(2017, 10, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("paul.newman@example.fr", "Paul Newman", REGEX_REF_CADEAUX_TRANCHE_AGE_20_30);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_20_30);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge30_40() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Carin")
                .prenom("Marie")
                .adressePostale("12 rue des Lilas")
                .email("marie.carin@example.fr")
                .dateNaissance(LocalDate.of(1980, 10, 8))
                .dateArriveeCommune(LocalDate.of(2016, 12, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("marie.carin@example.fr", "Marie Carin", REGEX_REF_CADEAUX_TRANCHE_AGE_30_40);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_30_40);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge40_50() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Dumond")
                .prenom("Michel")
                .adressePostale("18 square de Crusoe")
                .email("michel.dumond@example.fr")
                .dateNaissance(LocalDate.of(1970, 10, 25))
                .dateArriveeCommune(LocalDate.of(2016, 12, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("michel.dumond@example.fr", "Michel Dumond", REGEX_REF_CADEAUX_TRANCHE_AGE_40_50);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_40_50);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge50_60() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Avro")
                .prenom("Julien")
                .adressePostale("15 rue Apigi")
                .email("julien.avro@example.fr")
                .dateNaissance(LocalDate.of(1965, 6, 25))
                .dateArriveeCommune(LocalDate.of(2016, 12, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("julien.avro@example.fr", "Julien Avro", REGEX_REF_CADEAUX_TRANCHE_AGE_50_60);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_50_60);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge60_150() throws IOException, MessagingException {
        // Given
        Habitant habitant = Habitant.builder()
                .id(UUID.randomUUID().toString())
                .nom("Pascalin")
                .prenom("Yvette")
                .adressePostale("34 rue des Koali")
                .email("yvette.pascalin@example.fr")
                .dateNaissance(LocalDate.of(1958, 2, 14))
                .dateArriveeCommune(LocalDate.of(2016, 12, 1))
                .build();
        doReturn(newArrayList(habitant))
                .when(habitantRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(NOW_MINUS_ONE_YEAR);

        // When
        happyTownService.attribuerCadeaux(FILE_NAME, NOW, SMTP_HOST, SMTP_PORT);

        // Then
        verifyEmailsSent("yvette.pascalin@example.fr", "Yvette Pascalin", REGEX_REF_CADEAUX_TRANCHE_AGE_60_150);
        verifyHabitantSaved(REGEX_REF_CADEAUX_TRANCHE_AGE_60_150);
    }

    private void verifyEmailsSent(String destinataireHabitant, String nom, Pattern regExpRefCadeau) {
        assertThat(mailServer.getReceivedEmailSize()).isEqualTo(2);
        Iterator emails = mailServer.getReceivedEmail();

        SmtpMessage mailHabitant = (SmtpMessage) emails.next();
        String[] destinatairesHabitant = mailHabitant.getHeaderValues("To");
        assertThat(destinatairesHabitant.length).isEqualTo(1);
        assertThat(destinatairesHabitant[0]).isEqualTo(destinataireHabitant);
        assertThat(mailHabitant.getHeaderValue("Subject")).isEqualTo("Happy Birthday in HappyTown!");
        assertThat(mailHabitant.getBody()).contains(nom);
        assertThat(mailHabitant.getBody()).containsPattern(regExpRefCadeau);

        SmtpMessage mailMairieServiceCadeau = (SmtpMessage) emails.next();
        String[] destinatairesMairieServiceCadeau = mailMairieServiceCadeau.getHeaderValues("To");
        assertThat(destinatairesMairieServiceCadeau.length).isEqualTo(1);
        assertThat(destinatairesMairieServiceCadeau[0]).isEqualTo("mairie+service-cadeau@happytown.com");
        assertThat(mailMairieServiceCadeau.getHeaderValue("Subject")).isEqualTo("01/10/2018 - Synthese des cadeaux pour envoi");
        assertThat(mailMairieServiceCadeau.getBody()).contains(nom);
        assertThat(mailMairieServiceCadeau.getBody()).containsPattern(regExpRefCadeau);
    }

    private void verifyHabitantSaved(Pattern regExpRefCadeau) {
        ArgumentCaptor<Habitant> habitantArgumentCaptor = ArgumentCaptor.forClass(Habitant.class);
        verify(habitantRepository).save(habitantArgumentCaptor.capture());
        Habitant habitantSaved = habitantArgumentCaptor.getValue();
        assertThat(habitantSaved.getCadeauOffert()).containsPattern(regExpRefCadeau);
        assertThat(habitantSaved.getDateAttributionCadeau()).isEqualTo(NOW);
    }
}

