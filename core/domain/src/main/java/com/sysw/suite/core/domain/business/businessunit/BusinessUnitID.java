package com.sysw.suite.core.domain.business.businessunit;

import com.sysw.suite.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class BusinessUnitID extends Identifier {

    private final String value;

    public BusinessUnitID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static BusinessUnitID unique(){
        return BusinessUnitID.from(UUID.randomUUID());
    }

    public static BusinessUnitID from(final String anId){
        return new BusinessUnitID(anId);
    }

    public static BusinessUnitID from(final UUID anId){
        return new BusinessUnitID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BusinessUnitID that = (BusinessUnitID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
