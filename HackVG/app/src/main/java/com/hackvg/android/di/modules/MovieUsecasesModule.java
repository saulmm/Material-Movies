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
package com.hackvg.android.di.modules;

import com.hackvg.domain.GetMovieDetailUsecase;
import com.hackvg.domain.GetMovieDetailUsecaseController;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieUsecasesModule {

    private final String movieId;

    public MovieUsecasesModule(String movieId) {

        this.movieId = movieId;
    }

    @Provides GetMovieDetailUsecase provideGetMovieDetailUsecase (Bus bus, RestMovieSource movieSource) {
        return new GetMovieDetailUsecaseController(movieId, bus, movieSource);
    }
}
