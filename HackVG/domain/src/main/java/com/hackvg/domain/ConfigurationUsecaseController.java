package com.hackvg.domain;

import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ConfigurationResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


@SuppressWarnings("FieldCanBeLocal")
public class ConfigurationUsecaseController implements ConfigurationUsecase {

    private final String QUALITY_DESIRED = "w780";
    private final String QUALITY_ORIGINAL = "original";

    private final MediaDataSource mMediaDataSource;
    private final Bus mMainBus;

    @Inject
    public ConfigurationUsecaseController(MediaDataSource mediaDataSource, Bus mainBus) {

        mMediaDataSource = mediaDataSource;
        mMainBus = mainBus;

        mMainBus.register(this);
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

        mMainBus.unregister(this);
        configureImageUrl(configuration);
    }

    public void configureImageUrl (ConfigurationResponse configurationResponse) {

        String url;

        if (configurationResponse.getImages() != null) {

            String imageQuality = "";
            url = configurationResponse.getImages().getBase_url();

            for (String quality : configurationResponse.getImages().getBackdrop_sizes()) {

                if (quality.equals(QUALITY_DESIRED)) {

                    imageQuality = QUALITY_DESIRED;
                    break;
                }
            }

            if (imageQuality.equals(""))
                imageQuality = QUALITY_ORIGINAL;

            url += imageQuality;
            sendConfiguredUrlToPresenter(url);
        }
    }


    @Override
    public void sendConfiguredUrlToPresenter (String url) {

        mMainBus.post(url);
   }
}
