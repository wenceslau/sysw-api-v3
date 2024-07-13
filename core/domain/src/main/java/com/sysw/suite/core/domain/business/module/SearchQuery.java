package com.sysw.suite.core.domain.business.module;

import com.sysw.suite.core.pagination.Direction;
import com.sysw.suite.core.pagination.Operator;

public record SearchQuery(
        int page,
        int perPage,
        String sortBy,
        Direction direction,
        String[] fields,
        Operator[] operators,
        Object[] terms
) {

    public static SearchQuery with(
            int page,
            int perPage,
            String sortBy,
            Direction direction
    ) {
        return new SearchQuery(page, perPage, sortBy, direction, null, null, null);
    }

    public static SearchQuery with(
            int page,
            int perPage,
            String sortBy,
            Direction direction,
            String field,
            Operator operator,
            Object term
    ) {
        return new SearchQuery(page, perPage, sortBy, direction, new String[]{field},
                new Operator[]{operator}, new Object[]{term});
    }

    public static SearchQuery with(
            int page,
            int perPage,
            String sort,
            Direction direction,
            String[] fields,
            Operator[] operators,
            Object[] terms
    ) {
        return new SearchQuery(page, perPage, sort, direction, fields, operators, terms);
    }
}
