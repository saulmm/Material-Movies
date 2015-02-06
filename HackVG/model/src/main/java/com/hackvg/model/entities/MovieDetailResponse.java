
package com.hackvg.model.entities;

import java.util.List;

public class MovieDetailResponse {
    private boolean adult;
    private String backdrop_path;
    private Belongs_to_collection belongs_to_collection;
    private Number budget;
    private List genres;
    private String homepage;
    private Number id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private Number popularity;
    private String poster_path;
    private List production_companies;
    private List production_countries;
    private String release_date;
    private Number revenue;
    private Number runtime;
    private List spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private Number vote_average;
    private Number vote_count;

    public boolean getAdult() {

        return this.adult;
    }

    public void setAdult(boolean adult) {

        this.adult = adult;
    }

    public String getBackdrop_path() {

        return this.backdrop_path;
    }

    public Belongs_to_collection getBelongs_to_collection() {

        return this.belongs_to_collection;
    }

    public Number getBudget() {

        return this.budget;
    }

    public List getGenres() {

        return this.genres;
    }

    public String getHomepage() {

        return this.homepage;
    }

    public Number getId() {

        return this.id;
    }

    public String getImdb_id() {

        return this.imdb_id;
    }

    public String getOriginal_language() {

        return this.original_language;
    }

    public String getOriginal_title() {

        return this.original_title;
    }

    public String getOverview() {

        return this.overview;
    }

    public Number getPopularity() {

        return this.popularity;
    }

    public String getPoster_path() {

        return this.poster_path;
    }

    public List getProduction_companies() {

        return this.production_companies;
    }

    public List getProduction_countries() {

        return this.production_countries;
    }

    public String getRelease_date() {

        return this.release_date;
    }

    public Number getRevenue() {

        return this.revenue;
    }

    public Number getRuntime() {

        return this.runtime;
    }

    public List getSpoken_languages() {

        return this.spoken_languages;
    }

    public String getStatus() {

        return this.status;
    }

    public String getTagline() {

        return this.tagline;
    }

    public String getTitle() {

        return this.title;
    }

    public boolean getVideo() {

        return this.video;
    }

    public Number getVote_average() {

        return this.vote_average;
    }

    public Number getVote_count() {

        return this.vote_count;
    }
}
