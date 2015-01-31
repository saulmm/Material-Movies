
package com.hackvg.android.model.entities;

import java.util.List;

public class MovieDetailResponse{
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

 	public boolean getAdult(){
		return this.adult;
	}
	public void setAdult(boolean adult){
		this.adult = adult;
	}
 	public String getBackdrop_path(){
		return this.backdrop_path;
	}
	public void setBackdrop_path(String backdrop_path){
		this.backdrop_path = backdrop_path;
	}
 	public Belongs_to_collection getBelongs_to_collection(){
		return this.belongs_to_collection;
	}
	public void setBelongs_to_collection(Belongs_to_collection belongs_to_collection){
		this.belongs_to_collection = belongs_to_collection;
	}
 	public Number getBudget(){
		return this.budget;
	}
	public void setBudget(Number budget){
		this.budget = budget;
	}
 	public List getGenres(){
		return this.genres;
	}
	public void setGenres(List genres){
		this.genres = genres;
	}
 	public String getHomepage(){
		return this.homepage;
	}
	public void setHomepage(String homepage){
		this.homepage = homepage;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getImdb_id(){
		return this.imdb_id;
	}
	public void setImdb_id(String imdb_id){
		this.imdb_id = imdb_id;
	}
 	public String getOriginal_language(){
		return this.original_language;
	}
	public void setOriginal_language(String original_language){
		this.original_language = original_language;
	}
 	public String getOriginal_title(){
		return this.original_title;
	}
	public void setOriginal_title(String original_title){
		this.original_title = original_title;
	}
 	public String getOverview(){
		return this.overview;
	}
	public void setOverview(String overview){
		this.overview = overview;
	}
 	public Number getPopularity(){
		return this.popularity;
	}
	public void setPopularity(Number popularity){
		this.popularity = popularity;
	}
 	public String getPoster_path(){
		return this.poster_path;
	}
	public void setPoster_path(String poster_path){
		this.poster_path = poster_path;
	}
 	public List getProduction_companies(){
		return this.production_companies;
	}
	public void setProduction_companies(List production_companies){
		this.production_companies = production_companies;
	}
 	public List getProduction_countries(){
		return this.production_countries;
	}
	public void setProduction_countries(List production_countries){
		this.production_countries = production_countries;
	}
 	public String getRelease_date(){
		return this.release_date;
	}
	public void setRelease_date(String release_date){
		this.release_date = release_date;
	}
 	public Number getRevenue(){
		return this.revenue;
	}
	public void setRevenue(Number revenue){
		this.revenue = revenue;
	}
 	public Number getRuntime(){
		return this.runtime;
	}
	public void setRuntime(Number runtime){
		this.runtime = runtime;
	}
 	public List getSpoken_languages(){
		return this.spoken_languages;
	}
	public void setSpoken_languages(List spoken_languages){
		this.spoken_languages = spoken_languages;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public String getTagline(){
		return this.tagline;
	}
	public void setTagline(String tagline){
		this.tagline = tagline;
	}
 	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
 	public boolean getVideo(){
		return this.video;
	}
	public void setVideo(boolean video){
		this.video = video;
	}
 	public Number getVote_average(){
		return this.vote_average;
	}
	public void setVote_average(Number vote_average){
		this.vote_average = vote_average;
	}
 	public Number getVote_count(){
		return this.vote_count;
	}
	public void setVote_count(Number vote_count){
		this.vote_count = vote_count;
	}
}
