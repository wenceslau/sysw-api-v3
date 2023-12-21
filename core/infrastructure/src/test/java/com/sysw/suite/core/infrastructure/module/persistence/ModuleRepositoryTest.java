package com.sysw.suite.core.infrastructure.module.persistence;


import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
public class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    // Test a repository method with a null name value, check the exception message and the property name.
    @Test
    public void giveAnInvalidNullName_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity.name";
        final var property = "name";

        final var module = Module.newModule("Name 1", "Display Name 1", "License 1", true);
        final var entity = ModuleJpaEntity.from(module);
        entity.setName(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> moduleRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());
    }

    // Test a repository method with a null create_at value, check the exception message and the property name.
    @Test
    public void giveAnInvalidNullCreatedAt_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity.createdAt";
        final var property = "createdAt";

        final var module = Module.newModule("Name 1", "Display Name 1", "License 1", true);
        final var entity = ModuleJpaEntity.from(module);
        entity.setCreatedAt(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> moduleRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());
    }

    // Test a repository method with a null update_at value, check the exception message and the property name.
    @Test
    public void giveAnInvalidNullUpdatedAt_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity.updatedAt";
        final var property = "updatedAt";

        final var module = Module.newModule("Name 1", "Display Name 1", "License 1", true);
        final var entity = ModuleJpaEntity.from(module);
        entity.setUpdatedAt(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> moduleRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());
    }


    // Test a repository method with a null id value, check the exception message and the property name.
    @Test
    public void giveAnInvalidNullId_whenCallsCreate_shouldReturnAnException() {
        final var message = "not-null property references a null or transient value : com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity.id";
        final var property = "id";

        final var module = Module.newModule("Name 1", "Display Name 1", "License 1", true);
        final var entity = ModuleJpaEntity.from(module);
        entity.setId(null);

        final var exception = assertThrows(DataIntegrityViolationException.class, () -> moduleRepository.save(entity));
        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(property, cause.getPropertyName());
        Assertions.assertEquals(message, exception.getCause().getMessage());
    }

}
