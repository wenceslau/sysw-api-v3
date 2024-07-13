package com.sysw.suite.core.application.retrieve.get;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleByIDUseCase;
import com.sysw.suite.core.application.module.retrieve.ModuleOutput;
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
public class GetModuleByIDUseCaseIT {

    @SpyBean
    private ModuleGateway moduleGateway;

    @Autowired
    private GetModuleByIDUseCase useCase;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    void givenInputId_whenCallGetModule_thenShouldReturnModule() {
        // Given
        String id = "123";
        Module expectedModule = Module.newModule(ModuleID.from(id), "testName", "testDisplayName", "testLicense", true);
        save(expectedModule);
        assertEquals(1, moduleRepository.count());

        // When
        ModuleOutput output = useCase.execute(id);

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
        save(module);
        assertEquals(1, moduleRepository.count());

        //When
        ModuleOutput result = useCase.execute("1");

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
        String anInvalidId = "1";

        //When
        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(anInvalidId));

        //Then
        assertTrue(exception.getMessage().contains("Module with ID 1 not found"));
    }

    private void save(Module... aCategory) {

        var categoryJpaEntities = Arrays.stream(aCategory)
                .map(ModuleJPA::from)
                .toList();

        moduleRepository.saveAll(categoryJpaEntities);
    }

}