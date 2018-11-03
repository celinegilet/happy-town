package com.happytown.application.rest;

import com.google.common.collect.Lists;
import com.happytown.domain.entities.Habitant;
import com.happytown.domain.use_cases.GetAllHabitants;
import com.happytown.fixtures.HabitantFixture;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HabitantAdapter.class)
class HabitantAdapterTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GetAllHabitants getAllHabitants;

    @Test
    void getAllHabitants() throws Exception {
        // Given
        Habitant habitant = HabitantFixture.aHabitantAvecCadeau();
        Mockito.when(getAllHabitants.execute()).thenReturn(Lists.newArrayList(habitant));

        // When Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/habitants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(habitant.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom", Is.is(habitant.getNom())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenom", Is.is(habitant.getPrenom())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Is.is(habitant.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateNaissance", Is.is(String.valueOf(habitant.getDateNaissance()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateArriveeCommune", Is.is(String.valueOf(habitant.getDateArriveeCommune()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].adressePostale", Is.is(habitant.getAdressePostale())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cadeauOffert", Is.is(habitant.getCadeauOffert())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateAttributionCadeau", Is.is(String.valueOf(habitant.getDateAttributionCadeau()))));
    }

}