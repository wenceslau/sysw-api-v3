package com.sysw.suite.core.domain.enums;

public enum Direction {
    ASC, DESC;
    public static Direction from(String value) {
        return Direction.valueOf(value.toUpperCase());
    }
}
