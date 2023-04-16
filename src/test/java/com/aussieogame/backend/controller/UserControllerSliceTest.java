/*
package com.aussieogame.backend.controller;

import com.aussieogame.backend.repo.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerSliceTest {
    private static final Faker faker = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("GET can return display name")
    void givenExisting_getDisplayName_thenOK() throws Exception {
        //given
        final String displayName = faker.name().username();
        mockPost(displayName);

        //when
        ResultActions mockActions = mockGet();

        //then
        mockActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(displayName));

    }


    @Test
    @DisplayName("GET will return 404 if the name is not in DB")
    void givenNotExisting_getDisplayName_thenNotFound() throws Exception {
        //given
        //when
        ResultActions mockActions = mockGet();
        //then
        mockActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Registration not found."));
    }

    @Test
    @DisplayName("GET will return 401 if there are no credentials")
    void givenNoJwt_getDisplayName_thenUnauthenticated() throws Exception {
        //given

        //when
        ResultActions mockActions = mockMvc.perform(get("/api/v1/user")
                .with(SecurityMockMvcRequestPostProcessors.anonymous())
        );
        //then
        mockActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("REQUIRES AUTHENTICATION"));
    }

    @Test
    @DisplayName("POST can create a registration with new Display Name")
    void givenValidName_postPlayerDisplayName_thenCreated() throws Exception {
        //given
        String displayName = faker.name().username();

        //when
        ResultActions mockActions = mockPost(displayName);

        //then
        mockActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value(displayName));
        //default subject of test JWTs is "user"
        assertThat(repo.existsByUsername("user")).isTrue();
        assertThat(repo.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("POST will return 409 Conflict with duplication username (subject)")
    void givenDuplicateName_postPlayerDisplayName_thenConflict() throws Exception {
        //given
        String displayName = faker.name().username();
        mockPost(displayName); //first one should be OK

        //when
        ResultActions mockActions = mockPost(displayName);

        //then
        mockActions
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Registration already exists."));
        assertThat(repo.count()).isEqualTo(1);
    }

    private ResultActions mockGet() throws Exception {
        return mockMvc
                .perform(get("/api/v1/user")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                );
    }

    private ResultActions mockPost(String displayName) throws Exception {
        return mockMvc.perform(post("/api/v1/user")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", displayName)
        );
    }
}*/
