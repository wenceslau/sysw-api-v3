package com.sysw.suite.core.application.module.retrieve.get;

import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleID;

import java.time.Instant;

public record ModuleGetOutput(ModuleID id,
                              String name,
                              String displayName,
                              String license,
                              boolean active,
                              Instant createdAt,
                              Instant updateAt) {

    public static ModuleGetOutput from(Module anModule){
        return new ModuleGetOutput(
                anModule.getId(),
                anModule.getName(),
                anModule.getDisplayName(),
                anModule.getLicense(),
                anModule.isActive(),
                anModule.getCreatedAt(),
                anModule.getUpdatedAt()
        );
    }

    public static ModuleGetOutput with(final ModuleID id,
                                       final String aName,
                                       final String aDisplayName,
                                       final String aLicense,
                                       final boolean isActive,
                                       final Instant createdAt,
                                       final Instant updateAt) {
        return new ModuleGetOutput(id, aName, aDisplayName, aLicense, isActive, createdAt, updateAt);
    }
}
