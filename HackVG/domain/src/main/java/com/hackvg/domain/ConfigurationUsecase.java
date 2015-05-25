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

import com.hackvg.model.entities.ConfigurationResponse;

/**
 * Representation of an use case to get the configuration parameters
 * to use with the MovieDatabase api, such as the image endpoint
 */
@SuppressWarnings("UnusedDeclaration")
public interface ConfigurationUsecase extends Usecase {

    /**
     * Request data source the configuration data
     */
    void requestConfiguration ();

    /**
     * Callback used to be notified when the configuration data has been received
     *
     * @param configurationResponse the configuration with the data about the endpoint
     * of the images
     */
    void onConfigurationReceived (ConfigurationResponse configurationResponse);

    /**
     * Configures the endpoint used to retrieve images from the movie database api
     *
     * @param configurationResponse the configuration with the data about the endpoint of the images
     */
    void configureImageUrl (ConfigurationResponse configurationResponse);

    /**
     * Sends a configured to request images from the movie database api
     *
     * @param url configurated url
     */
    void sendConfiguredUrlToPresenter(String url);
}
