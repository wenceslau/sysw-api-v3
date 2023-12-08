package com.sysw.suite.core.domain.module;

public record ModuleSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
