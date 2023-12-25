package com.sysw.suite.core.application.update;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.update.UpdateModuleInput;
import com.sysw.suite.core.application.module.update.UpdateModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
        var actualModule = moduleRepository.findById(output.id().getValue()).get();

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
                .map(ModuleJpaEntity::from)
                .toList();

        moduleRepository.saveAll(categoryJpaEntities);
    }
}