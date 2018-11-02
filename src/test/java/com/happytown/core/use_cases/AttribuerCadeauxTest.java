package com.happytown.core.use_cases;

import com.happytown.core.entities.Cadeau;
import com.happytown.core.entities.Habitant;
import com.happytown.core.entities.TrancheAge;
import com.happytown.dataproviders.file.CadeauxByTrancheAgeFileProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_0_3;
import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_3_6;
import static com.happytown.fixtures.TrancheAgeFixture.trancheAge_60_150;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttribuerCadeauxTest {

    @InjectMocks
    AttribuerCadeaux attribuerCadeaux;

    @Mock
    HabitantProvider habitantProvider;

    @Mock
    NotificationProvider notificationProvider;

    @Mock
    CadeauxByTrancheAgeProvider cadeauxByTrancheAgeProvider;

    @Mock
    CadeauRandom cadeauRandom;

    private static Map<TrancheAge, List<Cadeau>> CADEAUX_BY_TRANCHE_AGE = new TreeMap<>();
    static {
        CadeauxByTrancheAgeFileProvider cadeauxByTrancheAgeFileProvider = new CadeauxByTrancheAgeFileProvider();
        try {
            Field pathFileCadeauxByTrancheAge = CadeauxByTrancheAgeFileProvider.class.getDeclaredField("pathFileCadeauxByTrancheAge");
            pathFileCadeauxByTrancheAge.setAccessible(true);
            pathFileCadeauxByTrancheAge.set(cadeauxByTrancheAgeFileProvider, "src/main/resources/cadeaux.txt");
            CADEAUX_BY_TRANCHE_AGE = cadeauxByTrancheAgeFileProvider.get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final LocalDate NOW = LocalDate.of(2018, 10, 1);
    private static final LocalDate NOW_MINUS_ONE_YEAR = LocalDate.of(2017, 10, 1);

    private static final List<Cadeau> CADEAUX_TRANCHE_AGE_0_3 = CADEAUX_BY_TRANCHE_AGE.get(trancheAge_0_3());
    private static final String REF_CADEAU_TRANCHE_AGE_0_3 = "70d73d02";

    private static final List<Cadeau> CADEAUX_TRANCHE_AGE_3_6 = CADEAUX_BY_TRANCHE_AGE.get(trancheAge_3_6());
    private static final String REF_CADEAU_TRANCHE_AGE_3_6 = "b3f83de3";

    private static final List<Cadeau> CADEAUX_TRANCHE_AGE_60_150 = CADEAUX_BY_TRANCHE_AGE.get(trancheAge_60_150());
    private static final String REF_CADEAU_TRANCHE_AGE_60_150 = "b9dcca0d";

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(Instant.parse("2018-10-01T10:15:30.00Z"), ZoneId.of("Europe/Paris"));
        attribuerCadeaux.setClock(clock);
        doReturn(CADEAUX_BY_TRANCHE_AGE).when(cadeauxByTrancheAgeProvider).get();
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge0_3() {
        // Given
        String id = UUID.randomUUID().toString();
        String nom = "Paron";
        String prenom = "Elise";
        String email = "elise.paron@example.fr";
        LocalDate dateNaissance = LocalDate.of(2018, 6, 22);
        LocalDate dateArriveeCommune = LocalDate.of(2017, 5, 1);
        String adressePostale = "48 faubourg de la Plage";
        Habitant habitant = new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
        doReturn(newArrayList(habitant)).when(habitantProvider).getEligiblesCadeaux(NOW_MINUS_ONE_YEAR);
        doReturn(CADEAUX_TRANCHE_AGE_0_3.get(0)).when(cadeauRandom).get(CADEAUX_TRANCHE_AGE_0_3);

        // When
        attribuerCadeaux.execute();

        // Then
        verifyNotificationsSent(habitant, REF_CADEAU_TRANCHE_AGE_0_3);
        verifyHabitantSaved(REF_CADEAU_TRANCHE_AGE_0_3);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge3_6() {
        // Given
        String id = UUID.randomUUID().toString();
        String nom = "Giron";
        String prenom = "Manon";
        String email = "manon.giron@example.fr";
        LocalDate dateNaissance = LocalDate.of(2012, 10, 2);
        LocalDate dateArriveeCommune = LocalDate.of(2017, 5, 1);
        String adressePostale = "2 rue des Apotres";
        Habitant habitant = new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
        doReturn(newArrayList(habitant)).when(habitantProvider).getEligiblesCadeaux(NOW_MINUS_ONE_YEAR);
        doReturn(CADEAUX_TRANCHE_AGE_3_6.get(0)).when(cadeauRandom).get(CADEAUX_TRANCHE_AGE_3_6);

        // When
        attribuerCadeaux.execute();

        // Then
        verifyNotificationsSent(habitant, REF_CADEAU_TRANCHE_AGE_3_6);
        verifyHabitantSaved(REF_CADEAU_TRANCHE_AGE_3_6);
    }

    @Test
    void attribuerCadeaux_habitantTrancheAge60_150() {
        // Given
        String id = UUID.randomUUID().toString();
        String nom = "Pascalin";
        String prenom = "Yvette";
        String email = "yvette.pascalin@example.fr";
        LocalDate dateNaissance = LocalDate.of(1958, 2, 14);
        LocalDate dateArriveeCommune = LocalDate.of(2016, 12, 1);
        String adressePostale = "12 rue des Kolin";
        Habitant habitant = new Habitant(id, nom, prenom, email, dateNaissance, dateArriveeCommune, adressePostale);
        doReturn(newArrayList(habitant)).when(habitantProvider).getEligiblesCadeaux(NOW_MINUS_ONE_YEAR);
        doReturn(CADEAUX_TRANCHE_AGE_60_150.get(0)).when(cadeauRandom).get(CADEAUX_TRANCHE_AGE_60_150);

        // When
        attribuerCadeaux.execute();

        // Then
        verifyNotificationsSent(habitant, REF_CADEAU_TRANCHE_AGE_60_150);
        verifyHabitantSaved(REF_CADEAU_TRANCHE_AGE_60_150);
    }

    private void verifyNotificationsSent(Habitant habitant, String refCadeau) {
        ArgumentCaptor<String> toCaptor = forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = forClass(String.class);
        ArgumentCaptor<String> templatePathCaptor = forClass(String.class);
        ArgumentCaptor<Map<String, String>> templateArgsCaptor = forClass(Map.class);

        verify(notificationProvider, times(2)).notifier(toCaptor.capture(), subjectCaptor.capture(), templatePathCaptor.capture(), templateArgsCaptor.capture());

        assertThat(toCaptor.getAllValues().get(0)).isEqualTo(habitant.getEmail());
        assertThat(subjectCaptor.getAllValues().get(0)).isEqualTo("Happy Birthday in HappyTown!");
        assertThat(templatePathCaptor.getAllValues().get(0)).isEqualTo("src/main/resources/messageCadeau.txt");
        assertThat(templateArgsCaptor.getAllValues().get(0).get("nom")).isEqualTo(habitant.getNom());
        assertThat(templateArgsCaptor.getAllValues().get(0).get("prenom")).isEqualTo(habitant.getPrenom());
        assertThat(templateArgsCaptor.getAllValues().get(0).get("cadeau")).isEqualTo(habitant.getCadeauOffert());

        assertThat(toCaptor.getAllValues().get(1)).isEqualTo("mairie+service-cadeau@happytown.com");
        assertThat(subjectCaptor.getAllValues().get(1)).isEqualTo("01/10/2018 - Synthese des cadeaux pour envoi");
        assertThat(templatePathCaptor.getAllValues().get(1)).isEqualTo("src/main/resources/messageRecapCadeaux.txt");
        assertThat(templateArgsCaptor.getAllValues().get(1).get("cadeaux")).contains(habitant.getPrenom() + " " + habitant.getNom() + " : " + habitant.getCadeauOffert());
        assertThat(templateArgsCaptor.getAllValues().get(1).get("cadeaux")).contains(refCadeau);
    }

    private void verifyHabitantSaved(String refCadeau) {
        ArgumentCaptor<Habitant> habitantArgumentCaptor = forClass(Habitant.class);
        verify(habitantProvider).save(habitantArgumentCaptor.capture());
        Habitant habitantSaved = habitantArgumentCaptor.getValue();
        assertThat(habitantSaved.getCadeauOffert()).containsPattern(refCadeau);
        assertThat(habitantSaved.getDateAttributionCadeau()).isEqualTo(NOW);
    }

}
