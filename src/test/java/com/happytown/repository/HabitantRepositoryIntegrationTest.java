package com.happytown.repository;

import com.happytown.domain.Habitant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HabitantRepositoryIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    HabitantRepository habitantRepository;

    @Test
    void findAll_shouldReturnAllHabitantsOfHappyTown() {
        // When
        List<Habitant> habitants = habitantRepository.findAll();

        // Then
        assertThat(habitants).hasSize(3);
    }

    @Test
    void findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNull() {
        // Given
        Habitant firstHabitant = testEntityManager.find(Habitant.class, "5e18367a-1eb3-4b91-b87a-44cd210ef7ba");
        Habitant secondHabitant = testEntityManager.find(Habitant.class, "aebb21fa-b981-4baa-9668-52be5ea3ce90");
        LocalDate dateArriveeCommune = LocalDate.now().minusYears(1);

        // When
        List<Habitant> habitants = habitantRepository.findByDateArriveeCommuneLessThanEqualAndCadeauOffertIsNullAndDateAttributionCadeauIsNullOrderByDateArriveeCommune(dateArriveeCommune);

        // Then
        assertThat(habitants).hasSize(2);
        assertThat(habitants.get(0)).isEqualToComparingFieldByField(firstHabitant);
        assertThat(habitants.get(1)).isEqualToComparingFieldByField(secondHabitant);
    }
}