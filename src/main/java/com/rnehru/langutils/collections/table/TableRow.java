package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.List;

public class TableRow {

    private List<Object> items;

    TableRow(Object... items) {
        this.items = List.of(items);
    }

    public int size() {
        return items.size();
    }

    public List<Object> getItems() {
        return items;
    }

}
