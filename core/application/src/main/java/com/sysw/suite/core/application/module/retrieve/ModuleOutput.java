package com.sysw.suite.core.application.module.retrieve;

import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleID;

import java.time.Instant;

public record ModuleOutput(ModuleID id,
                           String name,
                           String displayName,
                           String license,
                           boolean active,
                           Instant createdAt,
                           Instant updateAt) {

    public static ModuleOutput from(Module anModule){
        return new ModuleOutput(
                anModule.getId(),
                anModule.getName(),
                anModule.getDisplayName(),
                anModule.getLicense(),
                anModule.isActive(),
                anModule.getCreatedAt(),
                anModule.getUpdatedAt()
        );
    }

    public static ModuleOutput with(final ModuleID id,
                                    final String aName,
                                    final String aDisplayName,
                                    final String aLicense,
                                    final boolean isActive,
                                    final Instant createdAt,
                                    final Instant updateAt) {
        return new ModuleOutput(id, aName, aDisplayName, aLicense, isActive, createdAt, updateAt);
    }
}
