package com.sysw.suite.core.domain.module;

import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModuleTest {

    @Test
    public void givenAValidParameter_whenCallCreate_shouldReturnAnApplication(){
        final var expectedName = "Application A";
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        Assertions.assertEquals(expectedName, actualApplication.getName());
        Assertions.assertEquals(expectedDisplayName, actualApplication.getDisplayName());
        Assertions.assertEquals(expectedLicense, actualApplication.getLicense());
        Assertions.assertTrue(actualApplication.isActive());
        Assertions.assertNotNull(actualApplication.getCreatedAt());
        Assertions.assertNotNull(actualApplication.getUpdatedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallCreate_shouldTrowAnError(){
        final String expectedName = null;
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'name' should not be null";

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        final var actualException = Assertions.assertThrows(DomainException.class,
                () -> actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedMessage, actualException.getErrors().get(0).getMessage());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallCreate_shouldTrowAnError(){
        final String expectedName = " ";
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'name' should not be empty";

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        final var actualException = Assertions.assertThrows(DomainException.class,
                () -> actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedMessage, actualException.getErrors().get(0).getMessage());
    }

    @Test
    public void givenAnInvalidNullDisplayName_whenCallCreate_shouldTrowAnError(){
        final var expectedName = "Application";
        final String expectedDisplayName = null;
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'display name' should not be null";

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () ->
                actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedMessage, actualException.getErrors().get(0).getMessage());
    }

    @Test
    public void givenAnInvalidEmptyDisplayName_whenCallCreate_shouldTrowAnError(){
        final String expectedName = "Application";
        final var expectedDisplayName = " ";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'display name' should not be empty";

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () ->
                actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedMessage, actualException.getErrors().get(0).getMessage());
    }

    @Test
    public void givenAValidEmptyLicense_whenCallNewCategoryAndValidate_thenShouldReturnApplication() {
        final String expectedName = "Application";
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, expectedActive);

        Assertions.assertDoesNotThrow(() -> actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualApplication);
        Assertions.assertEquals(expectedName, actualApplication.getName());
        Assertions.assertEquals(expectedDisplayName, actualApplication.getDisplayName());
        Assertions.assertEquals(expectedActive, actualApplication.isActive());
        Assertions.assertNotNull( actualApplication.getCreatedAt());
        Assertions.assertNotNull( actualApplication.getUpdatedAt());

    }

    @Test
    public void givenAnActiveApplication_whenCallDeactivate_thenShouldReturnApplicationInactive() {
        final String expectedName = "Application";
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = false;

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, true);

        var updateAt = actualApplication.getUpdatedAt();
        Assertions.assertTrue(actualApplication.isActive());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        var deactivatedApplication = actualApplication.inactive();

        Assertions.assertDoesNotThrow(() -> actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(deactivatedApplication);
        Assertions.assertEquals(expectedName, deactivatedApplication.getName());
        Assertions.assertEquals(expectedDisplayName, deactivatedApplication.getDisplayName());
        Assertions.assertEquals(expectedActive, deactivatedApplication.isActive());
        Assertions.assertTrue(updateAt.isBefore(deactivatedApplication.getUpdatedAt()));
        Assertions.assertNotNull( actualApplication.getCreatedAt());
        Assertions.assertNotNull( actualApplication.getUpdatedAt());

    }

    @Test
    public void givenAnInactiveApplication_whenCallDeactivate_thenShouldReturnApplicationActive() {
        final String expectedName = "Application";
        final var expectedDisplayName = "App A";
        final var expectedLicense = "QWER1234ZXCV";
        final var expectedActive = true;

        final var actualApplication = Module.create(
                expectedName, expectedDisplayName, expectedLicense, false);

        var updateAt = actualApplication.getUpdatedAt();
        Assertions.assertFalse(actualApplication.isActive());

        var deactivatedApplication = actualApplication.active();

        Assertions.assertDoesNotThrow(() -> actualApplication.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(deactivatedApplication);
        Assertions.assertEquals(expectedName, deactivatedApplication.getName());
        Assertions.assertEquals(expectedDisplayName, deactivatedApplication.getDisplayName());
        Assertions.assertEquals(expectedActive, deactivatedApplication.isActive());
        Assertions.assertTrue(updateAt.isBefore(deactivatedApplication.getUpdatedAt()));
        Assertions.assertNotNull( actualApplication.getCreatedAt());
        Assertions.assertNotNull( actualApplication.getUpdatedAt());

    }

}