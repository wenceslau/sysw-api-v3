package com.sysw.suite.core.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysw.suite.core.ControllerTest;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleByIDUseCase;
import com.sysw.suite.core.application.module.retrieve.list.ListModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.list.ModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.exception.DomainException;
import com.sysw.suite.core.exception.NotFoundException;
import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleID;
import com.sysw.suite.core.pagination.Pagination;
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

import java.util.List;

import static com.sysw.suite.core.pagination.Direction.ASC;
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
    private GetModuleByIDUseCase getModuleByIDUseCase;

    @MockBean
    private UpdateModuleUseCase updateModuleUseCase;

    @MockBean
    private ListModuleUseCase listModuleUseCase;


    @Test
    void test() {
        System.out.println("Test");
    }

    @Test
    public void givenAValidInput_whenCallsCreateModule_shouldReturnModuleId() throws Exception  {
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
        when(getModuleByIDUseCase.execute(any()))
                .thenReturn(com.sysw.suite.core.application.module.retrieve.ModuleOutput.from(module));

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
        when(getModuleByIDUseCase.execute(any()))
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

    @Test
    public void givenAValidInput_whenCallUpdateModule_shouldUpdate() throws Exception {
        //given
        var expectedId = "123";
        var expectedName = "name";
        var expectedDisplayName = "display name";
        var expectedLicense = "license";
        var expectedActive = true;

        final var contentRequest =
                new CreateModuleRequest(expectedName, expectedDisplayName, expectedLicense, expectedActive);

        //when
        Mockito.when(updateModuleUseCase.execute(any()))
                .thenReturn(UpdateModuleOutput.from(expectedId));

        var request = MockMvcRequestBuilders.put("/modules/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isNoContent())
                .andExpect(header().string("Location", "/modules/123"));

        verify(updateModuleUseCase, times(1)).execute(argThat(cmd -> {
            Assertions.assertEquals(expectedId, cmd.id());
            Assertions.assertEquals(expectedName, cmd.name());
            Assertions.assertEquals(expectedDisplayName, cmd.displayName());
            Assertions.assertEquals(expectedLicense, cmd.license());
            Assertions.assertEquals(expectedActive, cmd.active());
            return true;
        }));
    }

    @Test
    public void givenAnInvalidId_whenCallUpdateModule_shouldReturnNotFound() throws Exception {
        //given
        var expectedId = "123";
        var expectedName = "name";
        var expectedDisplayName = "display name";
        var expectedLicense = "license";
        var expectedActive = true;

        final var contentRequest =
                new CreateModuleRequest(expectedName, expectedDisplayName, expectedLicense, expectedActive);

        //when
        Mockito.when(updateModuleUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Module.class, ModuleID.from(expectedId)));

        var request = MockMvcRequestBuilders.put("/modules/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentRequest));

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo("The Module with id 123 was not found")));

        verify(updateModuleUseCase, times(1)).execute(argThat(cmd -> {
            Assertions.assertEquals(expectedId, cmd.id());
            Assertions.assertEquals(expectedName, cmd.name());
            Assertions.assertEquals(expectedDisplayName, cmd.displayName());
            Assertions.assertEquals(expectedLicense, cmd.license());
            Assertions.assertEquals(expectedActive, cmd.active());
            return true;
        }));
    }

    @Test
    public void givenAValidParams_whenCallListModules_shouldReturnModules() throws Exception {
        //give
        final var module = Module.newModule("name", "display name","license",true);
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;
        final var expectedItems = List.of(ModuleOutput.from(module));
        var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems);

        //when
        when(listModuleUseCase.execute(any()))
                .thenReturn(pagination);

        var request = MockMvcRequestBuilders.get("/modules")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("search", expectedTerms)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mockMvc.perform(request)
                .andDo(print());

        //then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(module.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(module.getName())))
                .andExpect(jsonPath("$.items[0].display_name", equalTo(module.getDisplayName())))
                .andExpect(jsonPath("$.items[0].license", equalTo(module.getLicense())))
                .andExpect(jsonPath("$.items[0].active", equalTo(true)))
                .andExpect(jsonPath("$.items[0].created_at", Matchers.notNullValue()))
                .andExpect(jsonPath("$.items[0].updated_at", Matchers.notNullValue()));


        verify(listModuleUseCase, times(1)).execute(argThat(cmd -> {
            Assertions.assertEquals(expectedPage, cmd.page());
            Assertions.assertEquals(expectedPerPage, cmd.perPage());
            Assertions.assertEquals(expectedSort, cmd.sortBy());
            Assertions.assertEquals(ASC, cmd.direction());
            return true;
        }));

    }
}
