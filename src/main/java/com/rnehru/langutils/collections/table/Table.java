package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

import java.util.Map;

/**
 * Table is a statically defined data structure which is immutable by default. Its purpose is to allow developers
 * to create a static reference data structure of keys and multiple values.
 *
 * Why use this instead of a HashMap you may ask, well its simple. This exposes a nicer API for searching using SQL
 * style queries which can be nicely combined.
 *
 * e.g.
 *
 * <pre>
 *
 * {@code Table t = new Table(
 *                 header("key1", "key2"),
 *                 row(1, 2),
 *                 row(3, 4)
 *         );
 * }
 * </pre>
 *
 * Note that the columns have to be common data-types for each row. For instance, column with header "key1" above must
 * have Integer types for each row.
 *
 */

public final class Table {

    private final TableHeader header;
    private final FList<TableRow> tableRows;

    /**
     * Used to create a new TableRow with data items.
     *
     * @param items items as varargs to be passed
     * @return new TableRow object containing those items
     */

    public static TableRow row(Object... items) {
        return new TableRow(items);
    }

    /**
     * Used to create a new TableHeader. Note there can only be one TableHeader for a given Table
     * @param headers String values to be used as headers
     * @return new TableHeader object containing the headers
     */

    public static TableHeader header(String... headers) {
        return new TableHeader(headers);
    }

    public Table(TableHeader tableHeader, TableRow... tableRows) {
        Class<?>[] classes = new Class<?>[tableHeader.size()];
        for (int i = 0; i < tableRows.length; i++) {
            if (tableRows[i].size() != tableHeader.size()) {
                throw new TableCreationException("Row " + i + " has invalid size");
            }
            for (int j = 0; j < tableRows[i].size(); j++) {
                Class<?> actualClass = tableRows[i].getItems().get(j).getClass();
                Class<?> expectedClass = classes[j];
                if (classes[j] == null) {
                    classes[j] = tableRows[i].getItems().get(j).getClass();
                } else if (!actualClass.equals(expectedClass)) {
                    throw new TableCreationException("Col: [" + j + "] Row: [" + i + "] has type [" + actualClass + "], expected [" + expectedClass + "]");
                }
            }
        }
        this.tableRows = FList.of(tableRows);
        this.header = tableHeader;
    }

    /**
     * Returns the TableRow from the Table by the index
     * @param idx index of the row in the table to fetch
     * @return the TableRow at that position
     * @throws IndexOutOfBoundsException if there is no row at that index
     */

    public final Map<String, Object> getRow(int idx) {
        return tableRows.get(idx).getItems().zipWith((item, index) -> header.getHeaders().get(index));
    }

    /**
     * Gets TableRows which satisfies a query
     * @param query The query to be matched
     * @return FList of matching TableRows which satisfy
     */

    public final FList<TableRow> getRowsWhere(TableQuery query) {
        return tableRows.filter(r -> query.evaluate(header, r));
    }


}
