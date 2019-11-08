package com.rnehru.langutils.collections.table;

public class TableQueryUtils {

    static class FalseQuery implements TableQuery {
        @Override
        public boolean evaluate(TableHeader header, TableRow r) {
            return false;
        }
    }

    static class TrueQuery implements TableQuery {
        @Override
        public boolean evaluate(TableHeader header, TableRow r) {
            return true;
        }
    }

}
