package com.sysw.suite.core.infrastructure.module;

import com.sysw.suite.core.infrastructure.MySQLGatewayTest;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
class ModuleMySQLGatewayTest {

    @Autowired
    private ModuleMySQLGateway moduleMySQLGateway;

    @Autowired
    private ModuleRepository moduleRepository;

//    @BeforeEach
//    void cleanUp() {
//        moduleRepository.deleteAll();
//    }

    @Test
    public void testInjectionDependencies() {
        assertNotNull(moduleMySQLGateway);
        assertNotNull(moduleRepository);
    }

}