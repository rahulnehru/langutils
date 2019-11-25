package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

public final class TableRow {

    private final FList<Object> items;

    TableRow(Object... items) {
        this.items = FList.of(items);
    }

    public int size() {
        return items.size();
    }

    public FList<Object> getItems() {
        return items;
    }

}
