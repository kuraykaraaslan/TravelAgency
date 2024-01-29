package org.agency.core;

import java.util.List;

public class PaginatedResult<T> {
    private List<T> data;
    private int total;

    // Constructor to initialize the PaginatedResult object with data and total
    // count
    public PaginatedResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    // Getter method to retrieve the data list
    public List<T> getData() {
        return data;
    }

    // Getter method to retrieve the total count
    public int getTotal() {
        return total;
    }
}
