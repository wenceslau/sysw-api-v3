package com.sysw.suite.core.infrastructure.module.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysw.suite.core.ControllerTest;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.domain.exception.DomainException;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ControllerTest
public class ControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateModuleUseCase useCase;

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
        Mockito.when(useCase.execute(any()))
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

        verify(useCase, times(1)).execute(argThat(cmd -> {
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
        Mockito.when(useCase.execute(any()))
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

        verify(useCase, times(1)).execute(argThat(cmd -> {
                    Assertions.assertEquals(expectedName, cmd.name());
                    Assertions.assertEquals(expectedDisplayName, cmd.displayName());
                    Assertions.assertEquals(expectedLicense, cmd.license());
                    Assertions.assertEquals(expectedActive, cmd.active());
                    return true;
                }
        ));
    }


}
