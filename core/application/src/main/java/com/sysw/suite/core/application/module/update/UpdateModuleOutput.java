package com.sysw.suite.core.application.module.update;

import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleID;

public record UpdateModuleOutput(ModuleID id) {
    public static UpdateModuleOutput from(final Module aModule){
        return new UpdateModuleOutput(aModule.getId());
    }
}
