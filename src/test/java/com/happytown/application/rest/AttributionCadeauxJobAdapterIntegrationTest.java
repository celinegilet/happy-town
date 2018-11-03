package com.happytown.application.rest;

import com.happytown.domain.use_cases.AttribuerCadeaux;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AttributionCadeauxAdapter.class)
class AttributionCadeauxJobAdapterIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AttribuerCadeaux attribuerCadeaux;

    @Test
    void attribuerCadeaux() throws Exception {
        // When
        mockMvc.perform(post("/api/attributionCadeaux"))
                .andExpect(status().isOk());
    }
}