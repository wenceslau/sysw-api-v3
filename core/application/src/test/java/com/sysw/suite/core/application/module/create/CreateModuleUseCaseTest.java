package com.sysw.suite.core.application.module.create;

import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateModuleUseCaseTest {
    @Mock
    private ModuleGateway moduleGateway;

    @InjectMocks
    private CreateModuleUseCase useCase;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(moduleGateway);
    }

    @Test
    void givenModuleInput_whenCallCreate_thenShouldReturnModuleId() {
        // Given
        CreateModuleInput input = CreateModuleInput.with("testName", "testDisplayName", "testLicense", true);
        Module expectedModule = Module.newModule(input.name(), input.displayName(), input.license(), input.active());

        // When
        when(moduleGateway.create(any(Module.class))).thenReturn(expectedModule);
        CreateModuleOutput output = useCase.execute(input);

        // Then
        assertNotNull(output);
        assertNotNull(output.id());
        //Assert mockito, check if create method was called once, and if the return are as we expected
        verify(moduleGateway, times(1)).create(argThat(module ->
                Objects.equals(input.name(), module.getName())
                        && Objects.equals(expectedModule.getDisplayName(), module.getDisplayName())
                        && Objects.equals(expectedModule.getLicense(), module.getLicense())
                        && Objects.equals(expectedModule.isActive(), module.isActive())
                        && Objects.nonNull(module.getId())
                        && Objects.nonNull(module.getCreatedAt())
                        && Objects.nonNull(module.getUpdatedAt())
        ));
        //verify(moduleGateway, times(1)).create(any(Module.class));
    }

    @Test
    void givenModuleInputWithEmptyName_whenCallCreate_thenShouldThrowDomainException() {
        // Given
        CreateModuleInput input = CreateModuleInput.with("", "testDisplayName", "testLicense", true);

        // When/Then
        var expectedErrorMessage = "'name' should not be empty";
        CreateModuleUseCase useCase = new CreateModuleUseCase(moduleGateway);
        DomainException actualException = assertThrows(DomainException.class, () -> useCase.execute(input));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

    }

    @Test
    void givenModuleInputWithRandomGatewayError_whenCallCreate_thenShouldThrowException() {
        // Given
        CreateModuleInput input = CreateModuleInput.with("testName", "testDisplayName", "testLicense", true);
        when(moduleGateway.create(any())).thenThrow(new IllegalStateException());

        // When/Then
        assertThrows(IllegalStateException.class, () -> useCase.execute(input));

        verify(moduleGateway, times(1)).create(any(Module.class));


    }
}