package com.sysw.suite.core.domain.businessunit;

import com.sysw.suite.core.domain.business.businessunit.BusinessUnit;
import com.sysw.suite.core.domain.business.businessunit.InternalCode;
import com.sysw.suite.core.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessUnitTest {

    @Test
    void givenAValidParameter_whenCallNewBusinessUnitWithoutModules_thenCreateNewBusinessUnit() {

        final var expectedName = "Name A";
        final var expectedTradeName = "Trade Name A";
        final var expectedInternalCode = InternalCode.from("123456789");
        final var expectedActive = true;

        final var actualBusinessUnit = BusinessUnit.newBusinessUnit(expectedName, expectedTradeName, expectedInternalCode,
                expectedActive);

        assertNotNull(actualBusinessUnit);
        assertEquals(expectedName, actualBusinessUnit.name());
        assertEquals(expectedTradeName, actualBusinessUnit.tradeName());
        assertEquals(expectedInternalCode, actualBusinessUnit.internalCode());
        assertEquals(expectedActive, actualBusinessUnit.active());
        assertNotNull(actualBusinessUnit.createdAt());
        assertNotNull(actualBusinessUnit.updatedAt());
        assertEquals(0, actualBusinessUnit.modules().size());
    }

    @Test
    void givenAnInvalidParameter_whenCallNewBusinessUnit_thenThrowDomainException() {
        // Given
        var expectedName = "";
        var expectedTradeName = "";
        var expectedNameError = "'name' should not be null or empty";
        var expectedTradeNameError = "'tradeName' should not be null or empty";
        var expectedInternalCodeError = "'internalCode' should not be null";

        // When
        var domainException = assertThrows(DomainException.class, () -> {
            BusinessUnit.newBusinessUnit(expectedName, expectedTradeName, null, true);
        });

        // Then
        assertNotNull(domainException);
        assertEquals(3, domainException.getErrors().size());

        assertTrue(domainException.getErrors().stream().anyMatch(e -> expectedNameError.equals(e.getMessage())));
        assertTrue(domainException.getErrors().stream().anyMatch(e -> expectedTradeNameError.equals(e.getMessage())));
        assertTrue(domainException.getErrors().stream().anyMatch(e -> expectedInternalCodeError.equals(e.getMessage())));

    }
}