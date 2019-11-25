package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

import java.util.Arrays;

public final class AndQuery<T> implements TableQuery<T> {

    private final FList<TableQuery<?>> queries;

    private AndQuery(FList<TableQuery<?>> queries) {
        this.queries = queries;
    }


    public static AndQuery and(TableQuery<?>... queries) {
        return new AndQuery<>(FList.of(Arrays.asList(queries)));
    }

    @Override
    public boolean evaluate(TableHeader header, TableRow r) {
        return queries.toArrayList().stream().allMatch(q -> q.evaluate(header, r));
    }
}
