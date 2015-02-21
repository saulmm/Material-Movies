package com.hackvg.domain;

import com.hackvg.common.utils.BusProvider;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ConfigurationResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * This class is an implementation of {@link ConfigurationUsecase}
 */
public class ConfigurationUsecaseController implements ConfigurationUsecase {

    private final String DESIRED_QUALITY = "w780";

    private final MediaDataSource mMediaDataSource;
    private final Bus mUiBus;

    /**
     * Constructor of the class.
     *
     * @param uiBus The bus to communicate the domain module and the app module
     * @param mediaDataSource The data source to retrieve the  configuariton
     */
    public ConfigurationUsecaseController(MediaDataSource mediaDataSource, Bus uiBus) {

        if (mediaDataSource == null)
            throw new IllegalArgumentException("Media data source cannot be null");

        if (uiBus == null)
            throw new IllegalArgumentException("Ui bus cannot be null");

        mMediaDataSource = mediaDataSource;
        mUiBus = uiBus;

        BusProvider.getRestBusInstance().register(this);
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

        BusProvider.getRestBusInstance().unregister(this);
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

        mUiBus.post(url);
   }
}
