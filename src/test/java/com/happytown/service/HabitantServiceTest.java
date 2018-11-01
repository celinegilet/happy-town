package com.happytown.service;

import com.happytown.core.entities.Habitant;
import com.happytown.fixtures.HabitantFixture;
import com.happytown.repository.HabitantRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HabitantServiceTest {

    @InjectMocks
    HabitantService habitantService;

    @Mock
    HabitantRepository habitantRepository;

    @Test
    void getAll_shouldReturnAllHabitants() {
        // Given
        List<Habitant> habitants = Lists.newArrayList(HabitantFixture.aHabitant());
        BDDMockito.doReturn(habitants).when(habitantRepository).findAll();

        // When
        List<Habitant> results = habitantService.getAll();

        // Then
        assertThat(results).containsExactlyElementsOf(habitants);
    }
}