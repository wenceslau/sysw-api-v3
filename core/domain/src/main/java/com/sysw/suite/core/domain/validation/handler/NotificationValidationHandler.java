package com.sysw.suite.core.domain.validation.handler;

import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationValidationHandler implements ValidationHandler {

    private final List<Error> errors;

    private NotificationValidationHandler(final List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationValidationHandler create() {
        return new NotificationValidationHandler(new ArrayList<>());
    }

    public static NotificationValidationHandler create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static NotificationValidationHandler create(final Error anError) {
        return new NotificationValidationHandler(new ArrayList<>()).append(anError);
    }

    @Override
    public NotificationValidationHandler append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public NotificationValidationHandler append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public NotificationValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}