package com.sysw.suite.core.domain.business.businessunit;

import com.sysw.suite.core.ValueObject;

import java.util.Objects;

public class InternalCode extends ValueObject {

    private final String value;

    private InternalCode(String value) {
        this.value = Objects.requireNonNull(value, "Internal code should not be null");
    }

    public static InternalCode from(String value) {
        return new InternalCode(value);
    }
}
