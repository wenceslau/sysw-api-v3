package com.sysw.suite.core.domain.business.businessunit;

import com.sysw.suite.core.validation.ValidationHandler;
import com.sysw.suite.core.validation.Validator;

public class BusinessUnitValidator extends Validator {

    private final BusinessUnit businessUnit;

    public BusinessUnitValidator(ValidationHandler aHandler, BusinessUnit businessUnit) {
        super(aHandler);
        this.businessUnit = businessUnit;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkTradeNameConstraints();
        checkInternalCodeConstraints();
    }

    private void checkNameConstraints() {
        var name = businessUnit.name();
        if (name == null || name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be null or empty"));
        }
    }

    private void checkTradeNameConstraints() {
        var tradeName = businessUnit.tradeName();
        if (tradeName == null || tradeName.isBlank()) {
            this.validationHandler().append(new Error("'tradeName' should not be null or empty"));
        }
    }

    private void checkInternalCodeConstraints() {
        var internalCode = businessUnit.internalCode();
        if (internalCode == null) {
            this.validationHandler().append(new Error("'internalCode' should not be null"));
        }
    }


}
