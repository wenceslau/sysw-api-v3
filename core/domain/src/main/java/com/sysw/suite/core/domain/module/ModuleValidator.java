package com.sysw.suite.core.domain.module;

import com.sysw.suite.core.domain.validation.ValidationHandler;
import com.sysw.suite.core.domain.validation.Validator;

public class ModuleValidator extends Validator {

    private final Module module;

    public ModuleValidator(ValidationHandler aHandler, Module module) {
        super(aHandler);
        this.module = module;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkDisplayNameConstraints();
    }

    private void checkDisplayNameConstraints() {
        final var displayName = module.getDisplayName();

        if (displayName == null){
            this.validationHandler().append(new Error("'display name' should not be null"));
        }
        if (displayName != null && displayName.isBlank()){
            this.validationHandler().append(new Error("'display name' should not be empty"));
        }
    }

    private void checkNameConstraints() {
        final var name = module.getName();
        if (name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
        }
        if (name != null && name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
        }
    }
}
