package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.FList;

public final class TableHeader {

    private final FList<String> headers;

    TableHeader(String... headers) {
        this.headers = FList.of(headers);
    }

    public int size() {
        return headers.size();
    }

    public FList<String> getHeaders() {
        return this.headers;
    }



}
