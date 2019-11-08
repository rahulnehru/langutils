package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

import java.util.Arrays;

public class OrQuery<T> implements TableQuery<T> {

    private FList<TableQuery<?>> queries;

    private OrQuery(FList<TableQuery<?>> queries) {
        this.queries = queries;
    }


    public static OrQuery or(TableQuery<?>... queries) {
        return new OrQuery<>(FList.of(Arrays.asList(queries)));
    }

    @Override
    public boolean evaluate(TableHeader header, TableRow r) {
        return queries.toArrayList().stream().anyMatch(q -> q.evaluate(header, r));
    }
}
