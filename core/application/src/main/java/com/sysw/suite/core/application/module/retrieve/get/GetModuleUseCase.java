package com.sysw.suite.core.application.module.retrieve.get;

import com.sysw.suite.core.application.UseCase;
import com.sysw.suite.core.application.module.create.CreateModuleInput;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.domain.validation.handler.NotificationValidationHandler;

import java.util.Objects;
import java.util.function.Supplier;

public class GetModuleUseCase extends UseCase<String, ModuleOutput> {

    private final ModuleGateway moduleGateway;

    public GetModuleUseCase(final ModuleGateway moduleGateway) {
        this.moduleGateway = Objects.requireNonNull(moduleGateway);
    }

    @Override
    public ModuleOutput execute(final String anId) {

        ModuleID aModuleID = ModuleID.from(anId);
        Module module = moduleGateway.findById(aModuleID).orElseThrow(notFound(aModuleID));

        return ModuleOutput.with(module.getId(), module.getName(), module.getDisplayName(),
                module.getLicense(), module.isActive(), module.getCreatedAt(), module.getUpdatedAt());
    }

    private Supplier<? extends RuntimeException> notFound(ModuleID aModuleID) {
        return () -> DomainException.with(new Error("Module with ID %s not found".formatted(aModuleID.getValue())));
    }
}
