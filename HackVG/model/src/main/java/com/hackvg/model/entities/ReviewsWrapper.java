package com.hackvg.model.entities;


import java.util.List;

// TODO Use a common wrapper with Reviews and Movies
public class ReviewsWrapper {

    private String id;
    private String page;
    private List<Review> results;
    private Number total_pages;
    private Number total_results;

    public String getId() {

        return id;
    }

    public String getPage() {

        return page;
    }

    public List<Review> getResults() {

        return results;
    }

    public Number getTotal_pages() {

        return total_pages;
    }

    public Number getTotal_results() {

        return total_results;
    }
}
