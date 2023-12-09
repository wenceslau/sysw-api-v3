package com.sysw.suite.core.application.module.retrieve.list;

import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleID;

import java.time.Instant;

public record ModuleListOutput(ModuleID id,
                               String name,
                               String displayName,
                               String license,
                               boolean active,
                               Instant createdAt,
                               Instant updateAt) {

    public static ModuleListOutput from(Module aModule) {
        return new ModuleListOutput(aModule.getId(),
                aModule.getName(),
                aModule.getDisplayName(),
                aModule.getLicense(),
                aModule.isActive(),
                aModule.getCreatedAt(),
                aModule.getUpdatedAt());
    }


}
