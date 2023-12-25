package com.sysw.suite.core.domain.exception;

import java.util.List;

public class DomainException extends NoStacktraceException {

    protected final List<Error> errors;

    protected DomainException(final String anMessage, final List<Error> errors) {
        super(anMessage, null);
        this.errors = errors;
    }

    public static DomainException with(final String anError){
        return new DomainException(anError, List.of(new Error(anError)));
    }

    public static DomainException with(final Error anError){
        return new DomainException(anError.getMessage(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors){
        String message = (anErrors.size() == 1 ? anErrors.get(0).getMessage() : anErrors.size() + " errors");
        return new DomainException(message, anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public String getFirstMessage(){
        if (!errors.isEmpty()){
            return errors.get(0).getMessage();
        }
        return "";
    }
}
