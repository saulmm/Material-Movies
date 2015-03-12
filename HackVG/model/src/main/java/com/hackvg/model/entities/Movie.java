
package com.hackvg.model.entities;

import java.io.Serializable;

@SuppressWarnings("UnusedDeclaration")
public class Movie implements Serializable {

    private String adult;
    private String backdrop_path;
    private String id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String popularity;
    private String title;
    private String vote_average;
    private String vote_count;
    private String overview;
    private boolean movieReady;

    public Movie(String id, String title, String overview) {

        this.id = id;
        this.title = title;
        this.overview = overview;
    }

    public String getAdult() {

        return adult;
    }

    public String getBackdrop_path() {

        return backdrop_path;
    }

    public String getId() {

        return id;
    }

    public String getOriginal_title() {

        return original_title;
    }

    public String getRelease_date() {

        return release_date;
    }

    public String getPoster_path() {

        return poster_path;
    }

    public String getPopularity() {

        return popularity;
    }

    public String getTitle() {

        return title;
    }

    public String getVote_average() {

        return vote_average;
    }

    public String getVote_count() {

        return vote_count;
    }

    public void setMovieReady(boolean movieReady) {

        this.movieReady = movieReady;
    }

    public boolean isMovieReady() {

        return movieReady;
    }
}
