package com.sysw.suite.core.application.module.update;

public record UpdateModuleInput(String id,
                                String name,
                                String displayName,
                                String license,
                                boolean active) {

    public static UpdateModuleInput with(final String id,
                                         final String aName,
                                         final String aDisplayName,
                                         final String aLicense,
                                         final boolean isActive) {
        return new UpdateModuleInput(id, aName, aDisplayName, aLicense, isActive);
    }
}
