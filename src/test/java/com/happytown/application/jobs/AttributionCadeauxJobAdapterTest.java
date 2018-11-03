package com.happytown.application.jobs;

import com.happytown.domain.use_cases.AttribuerCadeaux;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AttributionCadeauxJobAdapterTest {

    @InjectMocks
    AttributionCadeauxJobAdapter attributionCadeauxJobAdapter;

    @Mock
    AttribuerCadeaux attribuerCadeaux;

    @Test
    void execute_shouldTriggerAttribuerCadeauxUseCase() {
        // Given
        String fileName = "src/main/resources/cadeaux.txt";

        // When
        attributionCadeauxJobAdapter.execute();

        // Then
        verify(attribuerCadeaux).execute();
    }

}