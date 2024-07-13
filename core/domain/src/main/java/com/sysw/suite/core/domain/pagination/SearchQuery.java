package com.sysw.suite.core.pagination;

import java.util.List;

public final class SearchQuery {

    private final int page;
    private final int perPage;
    private final String sort;
    private final Direction direction;
    private final List<SearchTerm> terms;

    private SearchQuery(int page, int perPage, String sort, Direction direction, List<SearchTerm> terms) {
        this.page = page;
        this.perPage = perPage;
        this.sort = sort;
        this.direction = direction;
        this.terms = terms;
    }

    // Factory methods

    public static SearchQuery with(SearchTerm term) {
        return new SearchQuery(0, 0, null, Direction.ASC, List.of(term));

    }

    public static SearchQuery with(List<SearchTerm> terms) {

        return new SearchQuery(0, 0, null, Direction.ASC, terms);
    }

    public static SearchQuery with(int page, int perPage) {
        return new SearchQuery(page, perPage, null, Direction.ASC, List.of());
    }

    public static SearchQuery with(int page, int perPage, SearchTerm term) {
        return new SearchQuery(page, perPage, null, Direction.ASC, List.of(term));
    }

    public static SearchQuery with(int page, int perPage, List<SearchTerm> terms) {
        return new SearchQuery(page, perPage, null, Direction.ASC, terms);
    }

    public static SearchQuery with(int page, int perPage, String sort, Direction direction) {
        return new SearchQuery(page, perPage, sort, direction, List.of());
    }

    public static SearchQuery with(int page, int perPage, String sort, Direction direction, SearchTerm term) {
        return new SearchQuery(page, perPage, sort, direction, List.of(term));
    }

    public static SearchQuery with(int page, int perPage, String sort, Direction direction, List<SearchTerm> terms) {
        return new SearchQuery(page, perPage, sort, direction, terms);
    }

    public static SearchQuery empty() {

        return new SearchQuery(0, 0, null, Direction.ASC,  List.of());
    }

    // Getter record style

    public int page() {
        return page;
    }

    public int perPage() {
        return perPage;
    }

    public String sort() {
        return sort;
    }

    public Direction direction() {
        return direction;
    }

    public List<SearchTerm> terms() {
        return terms;
    }
}
