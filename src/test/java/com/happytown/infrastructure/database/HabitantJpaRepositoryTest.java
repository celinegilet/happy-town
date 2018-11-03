package com.happytown.infrastructure.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HabitantJpaRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    HabitantJpaRepository habitantJpaRepository;

    @Test
    void findAll_shouldReturnAllHabitantsOfHappyTown() {
        // When
        List<HabitantJpa> habitants = habitantJpaRepository.findAll();

        // Then
        assertThat(habitants).hasSize(3);
    }

    @Test
    void findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNull() {
        // Given
        HabitantJpa firstHabitant = testEntityManager.find(HabitantJpa.class, "5e18367a-1eb3-4b91-b87a-44cd210ef7ba");
        HabitantJpa secondHabitant = testEntityManager.find(HabitantJpa.class, "aebb21fa-b981-4baa-9668-52be5ea3ce90");
        LocalDate dateArriveeCommune = LocalDate.now().minusYears(1);

        // When
        List<HabitantJpa> habitants = habitantJpaRepository.findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(dateArriveeCommune);

        // Then
        assertThat(habitants).hasSize(2);
        assertThat(habitants.get(0)).isEqualToComparingFieldByField(firstHabitant);
        assertThat(habitants.get(1)).isEqualToComparingFieldByField(secondHabitant);
    }

}