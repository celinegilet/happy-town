package com.happytown.infrastructure.database;

import com.google.common.collect.Lists;
import com.happytown.domain.entities.Habitant;
import com.happytown.fixtures.HabitantFixture;
import com.happytown.fixtures.HabitantJpaFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HabitantDatabaseAdapterTest {

    @InjectMocks
    HabitantDatabaseAdapter habitantDatabaseAdapter;

    @Mock
    HabitantJpaRepository habitantJpaRepository;

    @Test
    void getAll_shouldReturnHabitantsFromRepository() {
        // Given
        HabitantJpa habitantJpa = HabitantJpaFixture.aHabitantJpaSansCadeau();
        List<HabitantJpa> habitantsJpas = Lists.newArrayList(habitantJpa);
        doReturn(habitantsJpas).when(habitantJpaRepository).findAll();

        // When
        List<Habitant> results = habitantDatabaseAdapter.getAll();

        // Then
        Habitant habitant = results.get(0);
        assertThat(habitant).isEqualToComparingFieldByField(habitantJpa);
    }

    @Test
    void getEligiblesCadeaux_shouldReturnHabitantsFromRepository() {
        // Given
        HabitantJpa habitantJpa = HabitantJpaFixture.aHabitantJpaSansCadeau();
        List<HabitantJpa> habitantsJpas = Lists.newArrayList(habitantJpa);
        LocalDate dateArriveeCommune = LocalDate.now().minusYears(1);
        doReturn(habitantsJpas)
                .when(habitantJpaRepository)
                .findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(dateArriveeCommune);

        // When
        List<Habitant> results = habitantDatabaseAdapter.getEligiblesCadeaux(dateArriveeCommune);

        // Then
        Habitant habitant = results.get(0);
        assertThat(habitant).isEqualToComparingFieldByField(habitantJpa);
    }

    @Test
    void save_shouldSaveByUsingJpaRepository() {
        // Given
        Habitant habitant = HabitantFixture.aHabitantAvecCadeau();
        ArgumentCaptor<HabitantJpa> habitantJpaArgumentCaptor = ArgumentCaptor.forClass(HabitantJpa.class);

        // When
        habitantDatabaseAdapter.save(habitant);

        // Then
        verify(habitantJpaRepository).save(habitantJpaArgumentCaptor.capture());
        assertThat(habitantJpaArgumentCaptor.getValue()).isEqualToComparingFieldByField(habitant);
    }

}