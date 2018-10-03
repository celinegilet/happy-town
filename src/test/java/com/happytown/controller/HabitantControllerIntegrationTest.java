package com.happytown.controller;

import com.happytown.domain.Habitant;
import com.happytown.fixtures.HabitantFixture;
import com.happytown.service.HabitantService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HabitantController.class)
class HabitantControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    HabitantService habitantService;

    @Test
    void getAllHabitants() throws Exception {
        // Given
        Habitant habitant = HabitantFixture.aHabitant();
        when(habitantService.getAll()).thenReturn(Lists.newArrayList(habitant));

        // When Then
        mockMvc.perform(get("/api/habitants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nom", is(habitant.getNom())));
    }

}