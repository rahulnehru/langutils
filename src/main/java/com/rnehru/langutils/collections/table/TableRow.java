package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

/**
 * TableRow is the representation of a row in a Table.
 */
public final class TableRow extends TableAttributes<Object> {

    TableRow(Object... items) {
        this.items = FList.of(items);
    }

    /**
     * Returns the values in the row
     * @return FList containing the values
     */
    public final FList<Object> getItems() {
        return items;
    }

}
