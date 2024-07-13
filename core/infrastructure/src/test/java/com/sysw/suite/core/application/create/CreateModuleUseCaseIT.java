package com.sysw.suite.core.application.create;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.create.CreateModuleInput;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.exception.DomainException;
import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleGateway;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateModuleUseCaseIT {

    @SpyBean
    private ModuleGateway moduleGateway;

    @Autowired
    private CreateModuleUseCase useCase;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    void givenModuleInput_whenCallCreate_thenShouldReturnModuleId() {
        // Given
        CreateModuleInput input = CreateModuleInput.with("testName", "testDisplayName", "testLicense", true);
        Module expectedModule = Module.newModule(input.name(), input.displayName(), input.license(), input.active());

        // When
        CreateModuleOutput output = useCase.execute(input);

        assertEquals(1, moduleRepository.count());

        // Then
        assertNotNull(output);
        assertNotNull(output.id());

        var actualModule = moduleRepository.findById(output.id()).get();
        assertEquals(expectedModule.getName(), actualModule.getName());
        assertEquals(expectedModule.getDisplayName(), actualModule.getDisplayName());
        assertEquals(expectedModule.getLicense(), actualModule.getLicense());
        assertEquals(expectedModule.isActive(), actualModule.isActive());
        assertNotNull(actualModule.getCreatedAt());
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

        assertEquals(0, moduleRepository.count());

    }

    @Test
    void givenModuleInputWithRandomGatewayError_whenCallCreate_thenShouldThrowException() {
        // Given
        CreateModuleInput input = CreateModuleInput.with("testName", "testDisplayName", "testLicense", true);

        // When/Then

        doThrow(new IllegalStateException())
                .when(moduleGateway).create(any());

        assertThrows(IllegalStateException.class, () -> useCase.execute(input));

        assertEquals(0, moduleRepository.count());

    }
}