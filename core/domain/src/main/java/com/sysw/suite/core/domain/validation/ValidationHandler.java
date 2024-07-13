package com.sysw.suite.core.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation anValidation);

    List<Error> getErrors();

    default boolean hasError(){
        return  getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError(){
        if (getErrors() != null && !getErrors().isEmpty()){
            return getErrors().get(0);
        }
        return null;
    }

    interface Validation{
        void validate();
    }
}


