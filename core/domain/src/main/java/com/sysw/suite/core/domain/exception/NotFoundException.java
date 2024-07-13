package com.sysw.suite.core.exception;

import com.sysw.suite.core.AggregateRoot;
import com.sysw.suite.core.Identifier;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {
    protected NotFoundException(String anMessage, List<Error> errors) {
        super(anMessage, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregateRootClass,
            final Identifier anId){
        final var anError = "The %s with id %s was not found".formatted(
                anAggregateRootClass.getSimpleName(), anId.getValue());
        return new NotFoundException(anError, Collections.emptyList());
    }
}
