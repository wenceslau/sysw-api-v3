package com.sysw.suite.audit.domain.useraction;

public record UserActionSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
