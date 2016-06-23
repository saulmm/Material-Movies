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
package com.hackvg.domain;

import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.rest.RestDataSource;
import com.squareup.otto.Bus;

import javax.inject.Inject;


public class GetMoviesUsecase {
    private final RestDataSource mDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;

    @Inject
    public GetMoviesUsecase(RestDataSource dataSource, Bus uiBus) {
        mDataSource = dataSource;
        mUiBus = uiBus;
        mUiBus.register(this);
    }

    public void requestPopularMovies() {

        mDataSource.getMoviesByPage(mCurrentPage);
    }

    public void sendMoviesToPresenter (MoviesWrapper response) {

        mUiBus.post(response);
    }

    public void unRegister() {
        mUiBus.unregister(this);
    }

    public void execute() {

        requestPopularMovies();
        mCurrentPage++;
    }
}
