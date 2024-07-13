package com.sysw.suite.core.application.update;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.update.UpdateModuleInput;
import com.sysw.suite.core.application.module.update.UpdateModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.exception.DomainException;
import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleGateway;
import com.sysw.suite.core.domain.business.module.ModuleID;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJPA;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@IntegrationTest
public class UpdateModuleUseCaseIT {

    @SpyBean
    private ModuleGateway moduleGateway;

    @Autowired
    private UpdateModuleUseCase useCase;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    void givenModuleInput_whenCallUpdate_thenShouldReturnModuleId() {
        // Given
        ModuleID id = ModuleID.from("123");
        UpdateModuleInput inputCreate = UpdateModuleInput.with("123","testName", "testDisplayName", "testLicense", true);
        Module createModule = Module.newModule(id, inputCreate.name(), inputCreate.displayName(), inputCreate.license(), inputCreate.active());
        save(createModule);
        assertEquals(1, moduleRepository.count());

        // When
        UpdateModuleInput inputUpdated = UpdateModuleInput.with("123","testNameChanged", "testDisplayNameChanged", "testLicense", true);
        UpdateModuleOutput output = useCase.execute(inputUpdated);
        assertEquals(1, moduleRepository.count());

        // Then
        assertNotNull(output);
        assertNotNull(output.id());

        Mockito.verify(moduleGateway, times(1)).findById(eq(id));
        var actualModule = moduleRepository.findById(output.id()).get();

        assertEquals(inputUpdated.name(), actualModule.getName());
        assertEquals(inputUpdated.displayName(), actualModule.getDisplayName());
        assertEquals(inputUpdated.license(), actualModule.getLicense());
        assertEquals(inputUpdated.active(), actualModule.isActive());
        assertNotNull(actualModule.getCreatedAt());

    }

    @Test
    void givenModuleInputWithIdNotFound_whenCallUpdate_thenShouldThrowDomainException() {
        //Given
        ModuleID id = ModuleID.from("123");
        UpdateModuleInput input = UpdateModuleInput.with(id.getValue(), "-", "-", "-", true);
        String expectedError = "Module with ID 123 not found";

        //When/Then
        DomainException domainException = assertThrows(DomainException.class, () -> useCase.execute(input));
        assertEquals(domainException.getFirstMessage(),expectedError);
        verify(moduleGateway, times(1)).findById(any());
        verify(moduleGateway, times(0)).update(any());

    }

    private void save(Module... aCategory) {

        var categoryJpaEntities = Arrays.stream(aCategory)
                .map(ModuleJPA::from)
                .toList();

        moduleRepository.saveAll(categoryJpaEntities);
    }
}