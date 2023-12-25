package com.sysw.suite.core.application;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {

    @Autowired
    private CreateModuleUseCase useCase;

    @Autowired
    private ModuleRepository repository;

    @Test
    public void testInjection() {
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }

}
