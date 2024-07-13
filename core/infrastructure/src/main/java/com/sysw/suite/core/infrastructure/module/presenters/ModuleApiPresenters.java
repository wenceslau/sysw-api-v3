package com.sysw.suite.core.infrastructure.module.presenters;

import com.sysw.suite.core.application.module.retrieve.ModuleOutput;
import com.sysw.suite.core.infrastructure.module.models.ModuleResponse;

public interface ModuleApiPresenters {

    static ModuleResponse present(final ModuleOutput moduleOutput){
        return new ModuleResponse(
                moduleOutput.id().getValue(),
                moduleOutput.name(),
                moduleOutput.displayName(),
                moduleOutput.license(),
                moduleOutput.active(),
                moduleOutput.createdAt(),
                moduleOutput.updateAt()
        );
    }

}
