package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Table {

    private TableHeader header;
    private FList<TableRow> tableRows;

    public static TableRow row(Object... items) {
        return new TableRow(items);
    }

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

    Map<String, Object> getRow(int idx) {
        return tableRows.get(idx).getItems().zipWith((item, index) -> header.getHeaders().get(index));
    }

    public FList<TableRow> getRowsWhere(TableQuery query) {
        return tableRows.filter(r -> query.evaluate(header, r));
    }


}
