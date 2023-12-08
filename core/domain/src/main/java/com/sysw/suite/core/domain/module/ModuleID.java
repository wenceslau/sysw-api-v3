package com.sysw.suite.core.domain.module;

import com.sysw.suite.core.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ModuleID extends Identifier {

    private final String value;

    public ModuleID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static ModuleID unique(){
        return ModuleID.from(UUID.randomUUID());
    }

    public static ModuleID from(final String anId){
        return new ModuleID(anId);
    }

    public static ModuleID from(final UUID anId){
        return new ModuleID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ModuleID that = (ModuleID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
