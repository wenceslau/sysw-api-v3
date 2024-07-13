package com.sysw.suite.core.application.module.create;

import com.sysw.suite.core.domain.business.module.Module;

public record CreateModuleOutput(String id) {
    public static CreateModuleOutput from(final Module anApplication){
        return new CreateModuleOutput(anApplication.getId().getValue());
    }

    public static CreateModuleOutput with(String anID) {
        return new CreateModuleOutput(anID);
    }
}
