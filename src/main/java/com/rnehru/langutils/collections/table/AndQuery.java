package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.List;

import java.util.Arrays;

public class AndQuery<T> implements TableQuery<T> {

    private List<TableQuery<?>> queries;

    private AndQuery(List<TableQuery<?>> queries) {
        this.queries = queries;
    }


    public static AndQuery and(TableQuery<?>... queries) {
        return new AndQuery<>(List.of(Arrays.asList(queries)));
    }

    @Override
    public boolean evaluate(TableHeader header, TableRow r) {
        return queries.toArrayList().stream().allMatch(q -> q.evaluate(header, r));
    }
}
