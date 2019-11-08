package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.List;

import java.util.Arrays;

public class OrQuery<T> implements TableQuery<T> {

    private List<TableQuery<?>> queries;

    private OrQuery(List<TableQuery<?>> queries) {
        this.queries = queries;
    }


    public static OrQuery or(TableQuery<?>... queries) {
        return new OrQuery<>(List.of(Arrays.asList(queries)));
    }

    @Override
    public boolean evaluate(TableHeader header, TableRow r) {
        return queries.toArrayList().stream().anyMatch(q -> q.evaluate(header, r));
    }
}
