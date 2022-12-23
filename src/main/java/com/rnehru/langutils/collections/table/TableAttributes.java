package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

public abstract class TableAttributes<T> {

    FList<T> items;

    /**
     * The size, or the number of columns in the table
     * @return the number of table columns
     */

    public final int size() {
        return items.size();
    }



}
