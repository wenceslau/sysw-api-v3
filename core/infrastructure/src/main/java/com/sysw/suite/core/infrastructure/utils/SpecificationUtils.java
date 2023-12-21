package com.sysw.suite.core.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(String attribute, String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.get(attribute)), like(value.toUpperCase()));
    }

    private static String like(String value) {
        return "%" + value + "%";
    }

}
