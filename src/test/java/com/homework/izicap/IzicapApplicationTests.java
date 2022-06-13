package com.homework.izicap;

import com.homework.izicap.config.ApiConfig;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ApiConfig.class)
class IzicapApplicationTests {

    private static final String PATH = "/etablissement";
    private static final String EXIST_SIRET_NUMBER="41339442000033";
    private static final String NOT_EXIST_SIRET_NUMBER="0000";


    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFetchEtabsBySiret() throws Exception {
        this.mockMvc.perform(get(PATH+"/find/siret/{siret}",EXIST_SIRET_NUMBER)).andExpect(status().isOk())
                .andExpect(jsonPath("$", IsNot.not(IsNull.nullValue())))
                .andExpect(result -> Assertions.assertThat(
                                result.getResponse().getContentAsString())
                        .contains("\"id\":449554493,\"nic\":\"00033\""));
    }

    @Test
    public void returnsNotFoundForNoExistSiret() throws Exception {
        this.mockMvc.perform(get(PATH+"/find/siret/{siret}",NOT_EXIST_SIRET_NUMBER))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
