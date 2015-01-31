
package com.hackvg.android.model.entities;

import java.util.List;

public class TvShow {
   	private String backdrop_path;
   	private String first_air_date;
   	private Number id;
   	private String name;
   	private List origin_country;
   	private String original_name;
   	private Number popularity;
   	private String poster_path;
   	private Number vote_average;
   	private Number vote_count;

 	public String getBackdrop_path(){
		return this.backdrop_path;
	}
	public void setBackdrop_path(String backdrop_path){
		this.backdrop_path = backdrop_path;
	}
 	public String getFirst_air_date(){
		return this.first_air_date;
	}
	public void setFirst_air_date(String first_air_date){
		this.first_air_date = first_air_date;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public List getOrigin_country(){
		return this.origin_country;
	}
	public void setOrigin_country(List origin_country){
		this.origin_country = origin_country;
	}
 	public String getOriginal_name(){
		return this.original_name;
	}
	public void setOriginal_name(String original_name){
		this.original_name = original_name;
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
