package com.sysw.suite.core.infrastructure.module.presenters;

import com.sysw.suite.core.application.module.retrieve.get.ModuleGetOutput;
import com.sysw.suite.core.application.module.retrieve.list.ModuleListOutput;
import com.sysw.suite.core.infrastructure.module.models.ModuleGetResponse;
import com.sysw.suite.core.infrastructure.module.models.ModuleListResponse;

public interface ModuleApiPresenters {

    static ModuleGetResponse present(final ModuleGetOutput moduleGetOutput){
        return new ModuleGetResponse(
                moduleGetOutput.id().getValue(),
                moduleGetOutput.name(),
                moduleGetOutput.displayName(),
                moduleGetOutput.license(),
                moduleGetOutput.active(),
                moduleGetOutput.createdAt(),
                moduleGetOutput.updateAt()
        );
    }

    static ModuleListResponse present(final ModuleListOutput moduleOutput){
        return new ModuleListResponse(
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
