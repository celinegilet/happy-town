package com.happytown.domain.use_cases;

import com.google.common.collect.Lists;
import com.happytown.domain.entities.Habitant;
import com.happytown.fixtures.HabitantFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetAllHabitantsTest {

    @InjectMocks
    GetAllHabitants getAllHabitants;

    @Mock
    HabitantPort habitantPort;

    @Test
    void execute_shouldReturnHabitantsFromProvider() {
        // Given
        List<Habitant> habitants = Lists.newArrayList(HabitantFixture.aHabitantSansCadeau());
        doReturn(habitants).when(habitantPort).getAll();

        // When
        List<Habitant> results = getAllHabitants.execute();

        // Then
        assertThat(results).containsExactlyElementsOf(habitants);
    }
}