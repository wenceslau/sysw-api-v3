package com.sysw.suite.core.application.module.create;

public record CreateModuleInput(String name,
                                String displayName,
                                String license,
                                boolean active) {

    public static CreateModuleInput with(final String aName,
                                         final String aDisplayName,
                                         final String aLicense,
                                         final boolean isActive) {
        return new CreateModuleInput(aName, aDisplayName, aLicense, isActive);
    }
}
