package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

/**
 * TableHeader is the representation of the columns headings in a Table.
 */
public final class TableHeader extends TableAttributes<String> {

    TableHeader(String... headers) {
        this.items = FList.of(headers);
    }

    /**
     * Gets the headers as String values of the table
     * @return an FList of String values of the table.
     */

    public final FList<String> getHeaders() {
        return this.items;
    }

}
