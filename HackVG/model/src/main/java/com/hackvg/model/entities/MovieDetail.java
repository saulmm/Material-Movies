/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackvg.model.entities;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")

public class MovieDetail {
    private String backdrop_path;
    private Belongs_to_collection belongs_to_collection;
    private String homepage;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String release_date;
    private String status;
    private String tagline;
    private String title;
    private List production_countries;
    private List genres;
    private List spoken_languages;
    private List<Production_companies> production_companies;
    private List<ImagesWrapper.MovieImage> movieImagesList;
    private Number budget;
    private Number id;
    private Number popularity;
    private Number revenue;
    private Number runtime;
    private Number vote_average;
    private Number vote_count;
    private boolean adult;
    private boolean video;

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

    public List<Production_companies> getProduction_companies() {

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

    public void setMovieImagesList(List<ImagesWrapper.MovieImage> movieImagesList) {

        this.movieImagesList = movieImagesList;
    }

    public List<ImagesWrapper.MovieImage> getMovieImagesList() {

        return movieImagesList;
    }
}
