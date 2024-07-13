package com.sysw.suite.core.pagination;

public final class SearchTerm {

    private String field;
    private final Object value;
    private final Operator operator;

    private SearchTerm(String field, Object value, Operator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public static SearchTerm with(String field, Object term, Operator operator) {
        return new SearchTerm(field, term, operator);
    }

    public static SearchTerm with(String field, Object term) {
        return new SearchTerm(field, term, Operator.EQUAL);
    }

    public static SearchTerm empty() {
        return new SearchTerm(null, null, null);
    }

    public String field() {
        return field;
    }

    public Object value() {
        return value;
    }

    public Operator operator() {
        return operator;
    }

    public void updateField(String field) {
        this.field = field;
    }


}
