package com.sysw.suite.core.domain.module;

import com.sysw.suite.core.domain.enums.Direction;
import com.sysw.suite.core.domain.enums.Operator;

public record ModuleSearchQuery(
        int page,
        int perPage,
        String sortBy,
        Direction direction,
        String[] fields,
        Operator[] operators,
        Object[] terms
) {

    public static ModuleSearchQuery with(
            int page,
            int perPage,
            String sortBy,
            Direction direction
    ) {
        return new ModuleSearchQuery(page, perPage, sortBy, direction, null, null, null);
    }

    public static ModuleSearchQuery with(
            int page,
            int perPage,
            String sortBy,
            Direction direction,
            String field,
            Operator operator,
            Object term
    ) {
        return new ModuleSearchQuery(page, perPage, sortBy, direction, new String[]{field},
                new Operator[]{operator}, new Object[]{term});
    }

    public static ModuleSearchQuery with(
            int page,
            int perPage,
            String sort,
            Direction direction,
            String[] fields,
            Operator[] operators,
            Object[] terms
    ) {
        return new ModuleSearchQuery(page, perPage, sort, direction, fields, operators, terms);
    }
}
