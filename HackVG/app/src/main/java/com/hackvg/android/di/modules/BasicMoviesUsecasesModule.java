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

import com.hackvg.domain.ConfigurationUsecase;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.domain.GetMoviesUsecase;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.model.rest.RestMovieSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class BasicMoviesUsecasesModule {

    @Provides ConfigurationUsecase provideConfigurationUsecase (Bus bus, RestMovieSource moviesSource) {
        return new ConfigurationUsecaseController(moviesSource, bus);
    }

    @Provides GetMoviesUsecase provideMoviesUsecase (Bus bus, RestMovieSource movieSource) {
        return new GetMoviesUsecaseController(movieSource, bus);
    }
}
