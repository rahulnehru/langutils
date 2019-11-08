package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.table.TableQueryUtils.FalseQuery;
import com.rnehru.langutils.collections.table.TableQueryUtils.TrueQuery;
import org.junit.Test;

import static com.rnehru.langutils.collections.table.AndQuery.and;
import static com.rnehru.langutils.collections.table.Table.header;
import static com.rnehru.langutils.collections.table.Table.row;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class AndQueryTest {

    @Test
    public void evaluateReturnsTrueIfAllSubQueriesAreTrue() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row("foo", "bar");

        assertTrue(and(new TrueQuery(), new TrueQuery(), new TrueQuery()).evaluate(th, tr));
    }

    @Test
    public void evaluateReturnsFalseIfAllSubQueriesAreFalse() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row("foo", "bar");

        assertFalse(and(new FalseQuery(), new FalseQuery(), new FalseQuery()).evaluate(th, tr));
    }

    @Test
    public void evaluateReturnsFalseIfSomeSubQueriesAreFalse() {
        TableHeader th = header("key1", "key2");
        TableRow tr = row("foo", "bar");

        assertFalse(and(new FalseQuery(), new TrueQuery(), new TrueQuery()).evaluate(th, tr));
    }

}
