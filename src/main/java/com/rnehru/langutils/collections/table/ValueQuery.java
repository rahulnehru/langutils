package com.rnehru.langutils.collections.table;

import java.util.function.Predicate;

public final class ValueQuery<T> implements TableQuery<T> {

    private final String key;
    private final Predicate<T> p;

    private ValueQuery(String key, Predicate<T> p) {
        this.key = key;
        this.p = p;
    }

    /**
     * Static method allowing you to create a ValueQuery object which operates on a named table column
     * @param key column to operate on from the table header
     * @param p predicate function which is used to satisfy and match
     * @param <T> type of object in the column
     * @return new ValueQuery which can be evaluated
     */
    public static <T> ValueQuery valueOf(String key, Predicate<T> p) {
        return new ValueQuery<>(key, p);
    }

    @Override
    public boolean evaluate(TableHeader header, TableRow r) {
        int columnNumber = header.getHeaders().toArrayList().indexOf(key);
        if(columnNumber == -1) throw new QueryException("Could not find key "+key+" in table");
        return p.test((T) r.getItems().get(columnNumber));
    }
}
