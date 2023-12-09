package com.sysw.suite.audit.domain.useraction;

import com.sysw.suite.audit.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class UserActionID extends Identifier {

    private final String value;

    public UserActionID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UserActionID unique(){
        return UserActionID.from(UUID.randomUUID());
    }

    public static UserActionID from(final String anId){
        return new UserActionID(anId);
    }

    public static UserActionID from(final UUID anId){
        return new UserActionID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserActionID that = (UserActionID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
