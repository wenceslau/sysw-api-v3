package com.sysw.suite.core.infrastructure.module.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysw.suite.core.ControllerTest;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.ModuleOutput;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.exception.NotFoundException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.infrastructure.module.models.CreateModuleRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ControllerTest
public class ControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateModuleUseCase createModuleUseCase;

    @MockBean
    private GetModuleUseCase getModuleUseCase;


    @Test
    void test() {
        System.out.println("Test");
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        //given
        var expectedName = "name";
        var expectedDisplayName = "display name";
        var expectedLicense = "license";
        var expectedActive = true;

        final var contentRequest =
                new CreateModuleRequest(expectedName, expectedDisplayName, expectedLicense, expectedActive);

        //when
        Mockito.when(createModuleUseCase.execute(any()))
                .thenReturn(CreateModuleOutput.with("123"));

        var request = MockMvcRequestBuilders.post("/modules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/modules/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createModuleUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDisplayName, cmd.displayName());
                    Assertions.assertEquals(expectedLicense, cmd.license());
                    Assertions.assertEquals(expectedActive, cmd.active());
                    return true;
                }
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCategory_thenShouldThrowDomainException() throws Exception {
        //given
        var expectedName = "name";
        var expectedDisplayName = "display name";
        var expectedLicense = "license";
        var expectedActive = true;

        final var contentRequest =
                new CreateModuleRequest(expectedName, expectedDisplayName, expectedLicense, expectedActive);

        //when
        Mockito.when(createModuleUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error("'name' should not be null")));

        var request = MockMvcRequestBuilders.post("/modules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo("'name' should not be null")));

        verify(createModuleUseCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDisplayName, cmd.displayName());
                    Assertions.assertEquals(expectedLicense, cmd.license());
                    Assertions.assertEquals(expectedActive, cmd.active());
                    return true;
                }
        ));
    }

    @Test
    public void givenAnValidId_whenCallGetModuleById_thenShouldReturnModule() throws Exception {
        //given
        var expectedId = "123";
        var expectedName = "name";
        var expectedDisplayName = "display name";
        var expectedLicense = "license";
        var expectedActive = true;

        var module = Module.newModule(
                ModuleID.from(expectedId),
                expectedName,
                expectedDisplayName,
                expectedLicense,
                expectedActive
        );

        //when
        when(getModuleUseCase.execute(any()))
                .thenReturn(ModuleOutput.from(module));

        var request = MockMvcRequestBuilders.get("/modules/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.display_name", equalTo(expectedDisplayName)))
                .andExpect(jsonPath("$.license", equalTo(expectedLicense)))
                .andExpect(jsonPath("$.active", equalTo(expectedActive)))
                .andExpect(jsonPath("$.created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.updated_at", Matchers.notNullValue()));
    }

    @Test
    public void givenAnInvalidId_whenCallGetModule_shouldReturnNotFound() throws Exception {
        //given
        var expectedId = "123";
        var expectedErrorMessage = "The Module with id 123 was not found";

        //when
        when(getModuleUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Module.class, ModuleID.from(expectedId)));

        var request = MockMvcRequestBuilders.get("/modules/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

}
