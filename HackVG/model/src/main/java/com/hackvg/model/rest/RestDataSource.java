package com.hackvg.model.rest;

import com.hackvg.model.MediaDataSource;

/**
 * Created by saulmm on 25/02/15.
 */
public interface RestDataSource extends MediaDataSource {

    public void getMoviesByPage (int page);
}
