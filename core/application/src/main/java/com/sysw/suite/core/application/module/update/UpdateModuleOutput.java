package com.sysw.suite.core.application.module.update;

import com.sysw.suite.core.domain.module.Module;

public record UpdateModuleOutput(String id) {

    public static UpdateModuleOutput from(final String anId){
        return new UpdateModuleOutput(anId);
    }

    public static UpdateModuleOutput from(final Module aModule){
        return new UpdateModuleOutput(aModule.getId().getValue());
    }
}
