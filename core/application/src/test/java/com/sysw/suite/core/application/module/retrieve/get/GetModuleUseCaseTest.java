package com.sysw.suite.core.application.module.retrieve.get;

import com.sysw.suite.core.domain.exception.NotFoundException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetModuleUseCaseTest {
    @Mock
    private ModuleGateway moduleGateway;

    @InjectMocks
    private GetModuleUseCase useCase;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(moduleGateway);
    }

    @Test
    void givenInputId_whenCallGetModule_thenShouldReturnModule() {
        // Given
        String id = "123";
        Module expectedModule = Module.newModule(ModuleID.from(id), "testName", "testDisplayName", "testLicense", true);

        // When
        when(moduleGateway.findById(ModuleID.from(id))).thenReturn(Optional.of(expectedModule));
        ModuleGetOutput output = useCase.execute(id);

        // Then
        assertNotNull(output);
        Mockito.verify(moduleGateway, times(1)).findById(eq(ModuleID.from(id)));
        assertEquals(output.id(), expectedModule.getId());
        assertEquals(output.name(), expectedModule.getName());
        assertEquals(output.displayName(), expectedModule.getDisplayName());
        assertEquals(output.license(), expectedModule.getLicense());
        assertEquals(output.active(), expectedModule.isActive());

    }

    @Test
    void givenValidId_thenReturnModuleOutput() {
        //Given
        ModuleID moduleId = ModuleID.from("1");

        Module module = Module.newModule(moduleId, "module1", "moduleDisplayName", "license", true);
        when(moduleGateway.findById(moduleId)).thenReturn(Optional.of(module));

        //When
        ModuleGetOutput result = useCase.execute("1");

        //Then
        assertEquals(moduleId, result.id());
        assertEquals("module1", result.name());
        assertEquals("moduleDisplayName", result.displayName());
        assertEquals("license", result.license());
        assertTrue(result.active());
    }

    @Test
    void givenInvalidId_thenThrowsException() {
        //Given
        ModuleID moduleId = ModuleID.from("1");
        when(moduleGateway.findById(moduleId)).thenReturn(Optional.empty());

        //When
        var exception = assertThrows(NotFoundException.class, () -> useCase.execute("1"));

        //Then
        assertEquals("The Module with id 1 was not found", exception.getMessage());
    }

}