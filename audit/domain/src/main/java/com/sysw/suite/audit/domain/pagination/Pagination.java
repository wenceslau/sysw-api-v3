package com.sysw.suite.audit.domain.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> itens
) {

    public <R> Pagination<R> map(final Function<T, R> mapper){
        List<R> aNewList = this.itens().stream()
                .map(mapper)
                .toList();
        return new Pagination<>(currentPage(),perPage(), total(), aNewList);
    }

}
