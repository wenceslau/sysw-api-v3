package com.sysw.suite.core.application.module.create;

import com.sysw.suite.core.application.UseCase;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.validation.handler.NotificationValidationHandler;

import java.util.Objects;

public class CreateModuleUseCase extends UseCase<CreateModuleInput, CreateModuleOutput> {

    private final ModuleGateway moduleGateway;

    public CreateModuleUseCase(final ModuleGateway moduleGateway) {
        this.moduleGateway = Objects.requireNonNull(moduleGateway);
    }

    @Override
    public CreateModuleOutput execute(final CreateModuleInput aCommand) {

        final var aName = aCommand.name();
        final var aDisplayName = aCommand.displayName();
        final var aLicense = aCommand.license();
        final var isActive = aCommand.active();

        final var aModule = Module.create(aName, aDisplayName, aLicense, isActive);
        final var notificationHandler = NotificationValidationHandler.create();

        aModule.validate(notificationHandler);

        if (notificationHandler.hasError()){
            throw DomainException.with(notificationHandler.getErrors());
            //IMPORTANT I can use the pattern Either, pattern monad, where I can return two values in one like Optional.
            // Optional represent something with value or without value
            // Monad represents success or error, left or right
            // See vavr library.
        }

        return CreateModuleOutput.from(this.moduleGateway.create(aModule));
    }
}
