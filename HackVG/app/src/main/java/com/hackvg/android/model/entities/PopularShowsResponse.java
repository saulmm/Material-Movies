
package com.hackvg.android.model.entities;

import java.util.List;

public class PopularShowsResponse {
    private Number page;
    private List<TvShow> tvShows;
    private Number total_pages;
    private Number total_results;

    public Number getPage() {

        return this.page;
    }

    public void setPage(Number page) {

        this.page = page;
    }

    public List<TvShow> getTvShows() {

        return this.tvShows;
    }

    public void setTvShows(List<TvShow> tvShows) {

        this.tvShows = tvShows;
    }

    public Number getTotal_pages() {

        return this.total_pages;
    }

    public void setTotal_pages(Number total_pages) {

        this.total_pages = total_pages;
    }

    public Number getTotal_results() {

        return this.total_results;
    }

    public void setTotal_results(Number total_results) {

        this.total_results = total_results;
    }
}
