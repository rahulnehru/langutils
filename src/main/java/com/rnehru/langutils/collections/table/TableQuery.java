package com.rnehru.langutils.collections.table;

public interface TableQuery<T> {

    boolean evaluate(TableHeader header, TableRow r);

}
