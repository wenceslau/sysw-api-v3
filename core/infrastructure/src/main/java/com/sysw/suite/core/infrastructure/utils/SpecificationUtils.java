package com.sysw.suite.core.infrastructure.utils;

import com.sysw.suite.core.domain.enums.Operator;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;

import static com.sysw.suite.core.domain.enums.Operator.*;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> clause(String attribute, Operator operator, Object value) {

        switch (operator){
            case EQUAL:
                return equals(attribute,value);
            case IN:
                return in(attribute, value);
            case LIKE:
                return like(attribute, value);
            case LESS_THAN:
            case LESS_THAN_OR_EQUAL:
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                if (value instanceof Temporal) {
                    return getSpecificationForTemporal(attribute, value, operator);
                }
                if (value instanceof Number) {
                    return getSpecificationForNumbers(attribute, value, operator);
                }
                throw new IllegalArgumentException("Value is '%s' but must be a Number or Temporal".formatted(value.getClass().getSimpleName()));
            case IS_NULL:
                return (root, query, builder) -> builder.isNull(getPath((Root<String>)root, attribute));
            case IS_NOT_NULL:
                return (root, query, builder) -> builder.isNotNull(getPath((Root<String>)root, attribute));
            default:
                return (root, query, builder) -> builder.and();
        }

    }

    private static <T> Specification<T> like(String attribute, Object value) {
        if (value instanceof String) {
            return (root, query, builder) ->
                    builder.like(builder.upper(getPath((Root<String>)root, attribute)), "%" + ((String) value).toUpperCase() + "%");
        }
        throw new IllegalArgumentException("Value is '%s' but must be a String".formatted(value.getClass().getSimpleName()));
    }

    private static <T> Specification<T> equals(String attribute, Object value) {
        if (value instanceof String) {
            return (root, query, builder) ->
                    builder.equal(builder.upper(getPath((Root<String>)root, attribute)), ((String) value).toUpperCase());
        }

        return (root, query, builder) ->
                builder.equal(getPath((Root<String>)root, attribute), value);    }

    private static <T> Specification<T> in(String attribute, Object value) {
        if (value instanceof List) {
            List values = (List<String>) value;
            return (root, query, builder) -> getPath((Root<String>)root, attribute).in(values);
        }
        throw new IllegalArgumentException("Value is '%s' but must be a List".formatted(value.getClass().getSimpleName()));
    }

    private static <T> Path<T> getPath(Root<T> root, String attribute) {
        String[] objectFields = attribute.split("\\.");

        Path<T> path = root.get(objectFields[0]);

        for (int i = 1; i < objectFields.length; i++) {
            path = path.get(objectFields[i]);
        }

//        if (objectFields.length == 1) {
//            return root.get(objectFields[0]);
//        }
//        if (objectFields.length == 2) {
//            return root.get(objectFields[0]).get(objectFields[1]);
//        }
//        if (objectFields.length == 3) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]);
//        }
//        if (objectFields.length == 4) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]);
//        }
//        if (objectFields.length == 5) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]).get(objectFields[4]);
//        }

        return path;
    }

    private static <T> Specification<T> getSpecificationForNumbers(String attribute, Object value, Operator operator) {
        if (value instanceof Integer) {
            switch (operator){
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<Integer>) root, attribute), (Integer) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<Integer>) root, attribute), (Integer) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<Integer>) root, attribute), (Integer) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<Integer>) root, attribute), (Integer) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof Double) {
            switch (operator) {
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<Double>) root, attribute), (Double) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<Double>) root, attribute), (Double) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<Double>) root, attribute), (Double) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<Double>) root, attribute), (Double) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof Float) {
            switch (operator) {
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<Float>) root, attribute), (Float) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<Float>) root, attribute), (Float) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<Float>) root, attribute), (Float) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<Float>) root, attribute), (Float) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof BigDecimal) {
            switch (operator) {
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<BigDecimal>) root, attribute), (BigDecimal) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<BigDecimal>) root, attribute), (BigDecimal) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<BigDecimal>) root, attribute), (BigDecimal) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<BigDecimal>) root, attribute), (BigDecimal) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        throw new IllegalArgumentException("Value is '%s' but must be Number".formatted(value.getClass().getSimpleName()));
    }

    private static <T> Specification<T> getSpecificationForTemporal(String attribute, Object value, Operator operator) {
        if (value instanceof Instant) {
            switch (operator){
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<Instant>) root, attribute), (Instant) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<Instant>) root, attribute), (Instant) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<Instant>) root, attribute), (Instant) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<Instant>) root, attribute), (Instant) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof LocalTime) {
            switch (operator){
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<LocalTime>) root, attribute), (LocalTime) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<LocalTime>) root, attribute), (LocalTime) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<LocalTime>) root, attribute), (LocalTime) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<LocalTime>) root, attribute), (LocalTime) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof LocalDate) {
            switch (operator){
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<LocalDate>) root, attribute), (LocalDate) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<LocalDate>) root, attribute), (LocalDate) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<LocalDate>) root, attribute), (LocalDate) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<LocalDate>) root, attribute), (LocalDate) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        if (value instanceof LocalDateTime) {
            switch (operator){
                case LESS_THAN:
                    return (root, query, builder) -> builder.lessThan(getPath((Root<LocalDateTime>) root, attribute), (LocalDateTime) value);
                case LESS_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.lessThanOrEqualTo(getPath((Root<LocalDateTime>) root, attribute), (LocalDateTime) value);
                case GREATER_THAN:
                    return (root, query, builder) -> builder.greaterThan(getPath((Root<LocalDateTime>) root, attribute), (LocalDateTime) value);
                case GREATER_THAN_OR_EQUAL:
                    return (root, query, builder) -> builder.greaterThanOrEqualTo(getPath((Root<LocalDateTime>) root, attribute), (LocalDateTime) value);
                default:
                    return (root, query, builder) -> builder.and();
            }
        }
        throw new IllegalArgumentException("Value is '%s' but must be Temporal".formatted(value.getClass().getSimpleName()));
    }

//    private static <T> Path<LocalDateTime> getExpression(String attribute, Root<T> root) {
//        String[] objectFields = attribute.split("\\.");
//
//        Path<LocalDateTime> path = root.get(objectFields[0]);
//        for (int i = 1; i < objectFields.length; i++) {
//            path = path.get(objectFields[i]);
//        }
//
//        if (objectFields.length == 1) {
//            return root.get(objectFields[0]);
//        }
//        if (objectFields.length == 2) {
//            return root.get(objectFields[0]).get(objectFields[1]);
//        }
//        if (objectFields.length == 3) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]);
//        }
//        if (objectFields.length == 4) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]);
//        }
//        if (objectFields.length == 5) {
//            return root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]).get(objectFields[4]);
//        }
//
//        return root.get(attribute);
//    }
//
//    private static <T> Expression<String> builderToUpper(String attribute, Root<T> root, CriteriaBuilder builder) {
//        String[] objectFields = attribute.split("\\.");
//        if (objectFields.length == 1) {
//            return builder.upper(root.get(objectFields[0]));
//        }
//        if (objectFields.length == 2) {
//            return builder.upper(root.get(objectFields[0]).get(objectFields[1]));
//        }
//        if (objectFields.length == 3) {
//            return builder.upper(root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]));
//        }
//        if (objectFields.length == 4) {
//            return builder.upper(root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]));
//        }
//        if (objectFields.length == 5) {
//            return builder.upper(root.get(objectFields[0]).get(objectFields[1]).get(objectFields[2]).get(objectFields[3]).get(objectFields[4]));
//        }
//        return builder.upper(root.get(attribute));
//    }
//

}
