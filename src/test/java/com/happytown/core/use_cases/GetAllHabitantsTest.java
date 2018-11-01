package com.happytown.core.use_cases;

import com.happytown.core.entities.Habitant;
import com.happytown.fixtures.HabitantFixture;
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
class GetAllHabitantsTest {

    @InjectMocks
    GetAllHabitants getAllHabitants;

    @Mock
    HabitantProvider habitantProvider;

    @Test
    void execute_shouldReturnHabitantsFromProvider() {
        // Given
        List<Habitant> habitants = Lists.newArrayList(HabitantFixture.aHabitantSansCadeau());
        BDDMockito.doReturn(habitants).when(habitantProvider).getAll();

        // When
        List<Habitant> results = getAllHabitants.execute();

        // Then
        assertThat(results).containsExactlyElementsOf(habitants);
    }
}