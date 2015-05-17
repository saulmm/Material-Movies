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
    void requestConfiguration();

    /**
     * Callback used to be notified when the configuration data has been received
     *
     * @param configurationResponse the configuration with the data about the endpoint
     * of the images
     */
    void onConfigurationReceived(ConfigurationResponse configurationResponse);

    /**
     * Configures the endpoint used to retrieve images from the movie database api
     *
     * @param configurationResponse the configuration with the data about the endpoint of the images
     */
    void configureImageUrl(ConfigurationResponse configurationResponse);

    /**
     * Sends a configured to request images from the movie database api
     *
     * @param url configurated url
     */
    void sendConfiguredUrlToPresenter(String url);
}
