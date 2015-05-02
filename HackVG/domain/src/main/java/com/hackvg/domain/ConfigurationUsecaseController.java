package com.hackvg.domain;

import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ConfigurationResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * This class is an implementation of {@link ConfigurationUsecase}
 */
public class ConfigurationUsecaseController implements ConfigurationUsecase {

    private final String DESIRED_QUALITY = "w780";

    private final MediaDataSource mMediaDataSource;
    Bus bus;

    /**
     * Constructor of the class.
     *
     * @param uiBus The bus to communicate the domain module and the app module
     * @param mediaDataSource The data source to retrieve the  configuariton
     */
    @Inject
    public ConfigurationUsecaseController(MediaDataSource mediaDataSource, Bus uiBus) {

        if (mediaDataSource == null)
            throw new IllegalArgumentException("Media data source cannot be null");

        if (uiBus == null)
            throw new IllegalArgumentException("Ui bus cannot be null");

        bus = uiBus;
        mMediaDataSource = mediaDataSource;

        bus.register(this);
    }

    @Override
    public void requestConfiguration () {

        mMediaDataSource.getConfiguration();
    }

    @Override
    public void execute() {

        requestConfiguration();
    }

    @Subscribe
    @Override
    public void onConfigurationReceived(ConfigurationResponse configuration) {

        bus.unregister(this);
        configureImageUrl(configuration);
    }

    public void configureImageUrl (ConfigurationResponse configurationResponse) {

        String url = "";

        if (configurationResponse.getImages() != null) {

            url = configurationResponse.getImages()
                .getBase_url();
            String imageQuality = "";

            for (String quality : configurationResponse.getImages().getBackdrop_sizes()) {

                if (quality.equals(DESIRED_QUALITY)) {

                    imageQuality = DESIRED_QUALITY;
                    break;
                }
            }

            if (imageQuality.equals(""))
                imageQuality = "original";

            url += imageQuality;
            sendConfiguredUrlToPresenter(url);
        }
    }


    @Override
    public void sendConfiguredUrlToPresenter (String url) {

        bus.post(url);
   }
}
