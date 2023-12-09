package com.sysw.suite.core.application.module.update;

import com.sysw.suite.core.application.UseCase;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.domain.validation.handler.NotificationValidationHandler;

import java.util.function.Supplier;

public class UpdateModuleUseCase extends UseCase<UpdateModuleInput, UpdateModuleOutput> {

    private final ModuleGateway moduleGateway;

    public UpdateModuleUseCase(ModuleGateway moduleGateway) {
        this.moduleGateway = moduleGateway;
    }

    @Override
    public UpdateModuleOutput execute(UpdateModuleInput anInput) {

        ModuleID aModuleID = ModuleID.from(anInput.id());
        Module aModule = moduleGateway.findById(aModuleID).orElseThrow(notFound(aModuleID));

        aModule = aModule.update(anInput.name(), anInput.displayName(), anInput.license(), anInput.active());
        aModule = moduleGateway.update(aModule);

        NotificationValidationHandler validationHandler = NotificationValidationHandler.create();
        aModule.validate(validationHandler);

        return UpdateModuleOutput.from(aModule);
    }

    private Supplier<DomainException> notFound(ModuleID anId) {
        return () -> DomainException.with(new Error("Module with ID %s not found".formatted(anId.getValue())));
    }
}
