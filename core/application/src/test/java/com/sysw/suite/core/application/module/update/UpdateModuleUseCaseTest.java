package com.sysw.suite.core.application.module.update;

import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateModuleUseCaseTest {
    @Mock
    private ModuleGateway moduleGateway;

    @InjectMocks
    private UpdateModuleUseCase useCase;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(moduleGateway);
    }

    @Test
    void givenModuleInput_whenCallUpdate_thenShouldReturnModuleId() {
        // Given
        ModuleID id = ModuleID.from("123");
        UpdateModuleInput input = UpdateModuleInput.with("123","testName", "testDisplayName", "testLicense", true);
        Module updatedModule = Module.create(id, input.name(), input.displayName(), input.license(), input.active());

        // When
        when(moduleGateway.findById(ModuleID.from(input.id()))).thenReturn(Optional.of(updatedModule.clone()));
        when(moduleGateway.update(any(Module.class))).thenReturn(updatedModule);
        UpdateModuleOutput output = useCase.execute(input);

        // Then
        assertNotNull(output);
        assertNotNull(output.id());

        Mockito.verify(moduleGateway, times(1)).findById(eq(id));

        verify(moduleGateway, times(1)).update(argThat(module ->
                Objects.equals(module.getName(), updatedModule.getName())
                        && Objects.equals(module.getDisplayName(), updatedModule.getDisplayName())
                        && Objects.equals( module.getLicense(), updatedModule.getLicense())
                        && Objects.equals(module.isActive(), updatedModule.isActive())
                        && Objects.nonNull(module.getId())
                        && Objects.nonNull(module.getCreatedAt())
        ));
    }

    @Test
    void givenModuleInputWithIdNotFound_whenCallUpdate_thenShouldThrowDomainException() {
        //Given
        ModuleID id = ModuleID.from("123");
        UpdateModuleInput input = UpdateModuleInput.with(id.getValue(), "-", "-", "-", true);
        String expectedError = "Module with ID 123 not found";

        //When
        when(moduleGateway.findById(id)).thenReturn(Optional.empty());

        //Then
        DomainException domainException = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals(domainException.getFirstMessage(),expectedError);
        verify(moduleGateway, times(1)).findById(any());
        verify(moduleGateway, times(0)).update(any());

    }

    @Test
    public void testExecute() {
        final String moduleId = "moduleId";
        final String name = "name";
        final String displayName = "displayName";
        final String license = "license";
        final boolean isActive = true;

        Module moduleMock = Mockito.mock(Module.class);
        Mockito.when(moduleMock.update(name, displayName, license, isActive)).thenReturn(moduleMock);

        ModuleGateway moduleGatewayMock = Mockito.mock(ModuleGateway.class);
        Mockito.when(moduleGatewayMock.findById(ModuleID.from(moduleId))).thenReturn(Optional.of(moduleMock));
        Mockito.when(moduleGatewayMock.update(moduleMock)).thenReturn(moduleMock);

        UpdateModuleUseCase testObj = new UpdateModuleUseCase(moduleGatewayMock);
        UpdateModuleInput input = UpdateModuleInput.with(moduleId, name, displayName, license, isActive);
        UpdateModuleOutput output = testObj.execute(input);

        Mockito.verify(moduleMock).validate(Mockito.any());
        Mockito.verify(moduleGatewayMock).findById(Mockito.any());
        Mockito.verify(moduleGatewayMock).update(Mockito.any());

        Assertions.assertSame(moduleMock.getId(), output.id());
    }

    @Test
    public void testExecuteModuleNotFoundException() {
        final String moduleId = "moduleId";
        final String name = "name";
        final String displayName = "displayName";
        final String license = "license";
        final boolean isActive = true;

        ModuleGateway moduleGatewayMock = Mockito.mock(ModuleGateway.class);
        Mockito.when(moduleGatewayMock.findById(ModuleID.from(moduleId))).thenReturn(Optional.empty());

        UpdateModuleUseCase testObj = new UpdateModuleUseCase(moduleGatewayMock);
        UpdateModuleInput input = UpdateModuleInput.with(moduleId, name, displayName, license, isActive);

        Assertions.assertThrows(DomainException.class, () -> testObj.execute(input));

        Mockito.verify(moduleGatewayMock).findById(Mockito.any());
        Mockito.verifyNoMoreInteractions(moduleGatewayMock);
    }
}