package com.rnehru.langutils.collections.table;

import com.rnehru.langutils.collections.List;

public class TableHeader {

    private List<String> headers;

    TableHeader(String... headers) {
        this.headers = List.of(headers);
    }

    public int size() {
        return headers.size();
    }

    public List<String> getHeaders() {
        return this.headers;
    }



}
