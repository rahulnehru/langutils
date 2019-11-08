package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;
import com.rnehru.langutils.collections.table.TableQueryUtils.FalseQuery;
import com.rnehru.langutils.collections.table.TableQueryUtils.TrueQuery;
import org.junit.Test;

import java.util.Map;

import static com.rnehru.langutils.collections.table.AndQuery.and;
import static com.rnehru.langutils.collections.table.OrQuery.or;
import static com.rnehru.langutils.collections.table.Table.header;
import static com.rnehru.langutils.collections.table.Table.row;
import static com.rnehru.langutils.collections.table.ValueQuery.valueOf;
import static junit.framework.TestCase.assertEquals;

public class TableTest {

    @Test(expected = TableCreationException.class)
    public void tableThrowsExceptionWhenSizeOfRowsDoesNotMatchHeader() {
        new Table(
                header("key1", "key2"),
                row(1, 2, 5),
                row(3, 4, 5)
        );
    }

    @Test(expected = TableCreationException.class)
    public void tableThrowsExceptionObjectsInColumnsOfDifferentTypes() {
        new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, "foo")
        );
    }

    @Test
    public void tableCanBeCreatedWhenLengthOfRowsMatchesLengthOfHeaders() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, 4)
        );

        assertEquals(1, t.getRow(0).get("key1"));
        assertEquals(2, t.getRow(0).get("key2"));
        assertEquals(3, t.getRow(1).get("key1"));
        assertEquals(4, t.getRow(1).get("key2"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getRowThrowsExceptionWhenTableContainsFewerRowsThanIndex() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, 4)
        );

        t.getRow(6);
    }

    @Test
    public void getRowReturnsRowWhenIndexExistsInTable() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, 4)
        );

        Map<String, Object> r = t.getRow(1);
        assertEquals(3, r.get("key1"));
        assertEquals(4, r.get("key2"));
    }

    @Test
    public void getRowsWhereReturnsRowWhereTableQueryReturnsTrue() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, 4)
        );

        FList<TableRow> tr = t.getRowsWhere(new TrueQuery());
        assertEquals(2, tr.size());
        assertEquals(1, tr.get(0).getItems().get(0));
        assertEquals(2, tr.get(0).getItems().get(1));
        assertEquals(3, tr.get(1).getItems().get(0));
        assertEquals(4, tr.get(1).getItems().get(1));
    }

    @Test
    public void getRowsWhereReturnsNoRowsWhereTableQueryReturnsFalse() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 2),
                row(3, 4)
        );

        FList<TableRow> tr = t.getRowsWhere(new FalseQuery());
        assertEquals(0, tr.size());
    }



    @Test
    public void getWithPredicateReturnsCorrectRowAnd() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 4),
                row(3, 3),
                row(2, 6),
                row(5, 7),
                row(3, 3),
                row(2, 5),
                row(3, 4)
        );


        FList<TableRow> rows = t.getRowsWhere(
                and(
                        valueOf("key1", i -> (Integer) i < 3),
                        valueOf("key2", i -> (Integer) i < 5)
                )
        );

        assertEquals(1, rows.size()); // expecting row which is 1, 4
    }

    @Test
    public void getWithPredicateReturnsCorrectRowOr() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 4),
                row(3, 3),
                row(2, 6),
                row(5, 7),
                row(3, 3),
                row(2, 5),
                row(10, 10)
        );


        FList<TableRow> rows = t.getRowsWhere(
                or(
                        valueOf("key1", i -> (Integer) i < 3),
                        valueOf("key2", i -> (Integer) i < 5)
                )
        );


        assertEquals(5, rows.size()); // expecting row which is 1, 4
    }

}
