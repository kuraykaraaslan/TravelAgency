package org.agency.core;

import java.util.List;

public class PaginatedResult<T> {
    private List<T> data;
    private int total;

    public PaginatedResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    // Add getters for 'data' and 'total'
    public List<T> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}
