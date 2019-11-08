package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.List;
import com.rnehru.langutils.collections.table.*;
import org.junit.Test;

import static com.rnehru.langutils.collections.table.Table.header;
import static com.rnehru.langutils.collections.table.Table.row;
import static com.rnehru.langutils.collections.table.ValueQuery.valueOf;
import static junit.framework.TestCase.*;

public class ValueQueryTest {


    @Test
    public void evaluateReturnsFalseWhenValueQueryDoesNotMatch() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row(1, 2);

        TableQuery q = valueOf("key1", i -> (Integer) i > 5);
        assertFalse(q.evaluate(th, tr));
    }

    @Test
    public void evaluateReturnsTrueWhenValueQueryDoesMatch() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row(1, 2);

        TableQuery q = valueOf("key1", i -> (Integer) i == 1);
        assertTrue(q.evaluate(th, tr));
    }

    @Test(expected = QueryException.class)
    public void evaluateThrowsExceptionWhenKeyDoesNotExistInTable() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row(1, 2);

        valueOf("bla", i -> (Integer) i == 1).evaluate(th, tr);
    }

    @Test
    public void getRowsWhereReturnsCorrectRow() {
        Table t = new Table(
                header("key1", "key2"),
                row(1, 4),
                row(3, 3)
        );

        List<TableRow> rows = t.getRowsWhere(
                valueOf("key2", i -> (Integer) i < 4)
        );

        assertEquals(1, rows.size()); // expecting row which is 3,3
        assertEquals(3, rows.get(0).getItems().get(0));
        assertEquals(3, rows.get(0).getItems().get(1));
    }

}
