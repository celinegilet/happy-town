package com.happytown.entrypoints.jobs;

import com.happytown.core.use_cases.AttribuerCadeaux;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AttributionCadeauxJobTest {

    @InjectMocks
    AttributionCadeauxJob attributionCadeauxJob;

    @Mock
    AttribuerCadeaux attribuerCadeaux;

    @Test
    void execute_shouldTriggerAttribuerCadeauxUseCase() {
        // When
        attributionCadeauxJob.execute();

        // Then
        verify(attribuerCadeaux).execute();
    }
}